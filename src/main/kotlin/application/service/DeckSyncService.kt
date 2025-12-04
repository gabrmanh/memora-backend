package org.showoff.application.service

import org.showoff.application.dto.CardDTO
import org.showoff.application.dto.DeckSyncDTO
import org.showoff.application.dto.FieldDTO
import org.showoff.application.dto.FieldValueDTO
import org.showoff.entity.deck.Card
import org.showoff.entity.deck.Deck
import org.showoff.entity.deck.Field
import org.showoff.entity.deck.FieldValue
import org.showoff.entity.user.User
import org.showoff.entity.user.UserDeck
import org.showoff.persistence.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class DeckSyncService(
    private val deckRepository: DeckRepository,
    private val userRepository: UserRepository,
    private val userDeckRepository: UserDeckRepository,
    private val cardRepository: CardRepository,
    private val fieldRepository: FieldRepository,
    private val fieldValueRepository: FieldValueRepository
) {
    @Transactional
    open fun syncDeck(deckDTO: DeckSyncDTO): Deck {
        val user = userRepository.findById(deckDTO.createdById)
            .orElseThrow { IllegalArgumentException("User not found") }

        val deck = setDeck(deckDTO, user)

        val existingCardIds = cardRepository.findAllByDeckId(deck.id).map { it.id }
        if (existingCardIds.isNotEmpty()) {
            fieldValueRepository.deleteByCardIdIn(existingCardIds)
        }
        cardRepository.deleteByDeckId(deck.id)
        fieldRepository.deleteByDeckId(deck.id)

        val fieldsById = mutableMapOf<UUID, Field>()
        deckDTO.fields.forEach { f ->
            val field = Field(
                id = f.id,
                name = f.name,
                deck = deck,
                role = "FRONT"
            )
            fieldsById[field.id] = fieldRepository.save(field)
        }

        deckDTO.cards.forEach { c ->
            val card = cardRepository.save(
                Card(
                    id = c.id,
                    index = c.index,
                    deck = deck
                )
            )

            c.fieldValues.forEach { fv ->
                val field = fieldsById[fv.fieldId]
                    ?: throw IllegalArgumentException("Unknown field: ${fv.fieldId}")
                fieldValueRepository.save(
                    FieldValue(card = card, field = field, value = fv.value)
                )
            }
        }

        return deck
    }


    @Transactional(readOnly = true)
    open fun getDeckForSync(deckId: UUID): DeckSyncDTO {
        val deck = deckRepository.findById(deckId)
            .orElseThrow { IllegalArgumentException("Deck not found: $deckId") }

        val fields = fieldRepository.findAllByDeckId(deckId)
        val cards = cardRepository.findAllByDeckId(deckId)

        val cardIds = cards.map { it.id }
        val fieldValues = if (cardIds.isEmpty()) emptyList() else fieldValueRepository.findAllByCardIdIn(cardIds)

        val fvsByCardId: Map<UUID, List<FieldValue>> = fieldValues.groupBy { it.card!!.id }

        return DeckSyncDTO(
            id = deck.id,
            name = deck.name,
            description = deck.description,
            version = deck.version,
            createdById = deck.createdBy?.id ?: error("Deck.createdBy is null"),

            fields = fields.map { f ->
                FieldDTO(
                    id = f.id,
                    name = f.name,
                    role = f.role
                )
            },

            cards = cards.map { c ->
                val fvs = fvsByCardId[c.id].orEmpty()
                CardDTO(
                    id = c.id,
                    index = c.index,
                    fieldValues = fvs.map { fv ->
                        FieldValueDTO(
                            fieldId = fv.field!!.id,
                            value = fv.value
                        )
                    }
                )
            }
        )
    }

    private fun setDeck(
        deckDTO: DeckSyncDTO,
        user: User
    ): Deck {
        val existingDeck = deckRepository.findById(deckDTO.id)

        return if (existingDeck.isPresent) {
            val existing = existingDeck.get()
            val updated = existing.copy(
                name = deckDTO.name,
                description = deckDTO.description,
                version = existing.version + 1
            )
            deckRepository.save(updated)
        } else {
            val created = Deck(
                id = deckDTO.id,
                name = deckDTO.name,
                description = deckDTO.description,
                version = 1,
                createdBy = user
            )
            val savedDeck = deckRepository.save(created)
            userDeckRepository.save(UserDeck(role = "owner", user = user, deck = savedDeck))

            savedDeck
        }
    }
}