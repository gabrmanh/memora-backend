package org.showoff.application.service

import org.showoff.application.dto.DeckSyncDTO
import org.showoff.entity.deck.Card
import org.showoff.entity.deck.Deck
import org.showoff.entity.deck.Field
import org.showoff.entity.deck.FieldValue
import org.showoff.persistence.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class DeckSyncService(
    private val deckRepository: DeckRepository,
    private val userRepository: UserRepository,
    private val cardRepository: CardRepository,
    private val fieldRepository: FieldRepository,
    private val fieldValueRepository: FieldValueRepository
) {

    @Transactional
    open fun syncDeck(deckDTO: DeckSyncDTO): Deck {
        val user = userRepository.findById(deckDTO.createdById)
            .orElseThrow { IllegalArgumentException("User not found") }

        val deck = deckRepository.findById(deckDTO.id).orElse(
            Deck(
                id = deckDTO.id,
                name = deckDTO.name,
                description = deckDTO.description,
                createdBy = user
            )
        ).copy(
            name = deckDTO.name,
            description = deckDTO.description
        )
        deckRepository.save(deck)

        val fieldMap = mutableMapOf<UUID, Field>()
        for (fieldDTO in deckDTO.fields) {
            val field = fieldRepository.findById(fieldDTO.id).orElse(
                Field(
                    id = fieldDTO.id,
                    name = fieldDTO.name,
                    type = fieldDTO.type,
                    deck = deck
                )
            ).copy(
                name = fieldDTO.name,
                type = fieldDTO.type,
                deck = deck
            )
            fieldRepository.save(field)
            fieldMap[field.id] = field
        }

        for (cardDTO in deckDTO.cards) {
            val card = cardRepository.findById(cardDTO.id).orElse(
                Card(
                    id = cardDTO.id,
                    difficulty = cardDTO.difficulty,
                    index = cardDTO.index,
                    deck = deck
                )
            ).copy(
                difficulty = cardDTO.difficulty,
                index = cardDTO.index,
                deck = deck
            )
            cardRepository.save(card)

            for (fv in cardDTO.fieldValues) {
                val field = fieldMap[fv.fieldId]
                    ?: throw IllegalArgumentException("Unknown field: ${fv.fieldId}")
                val fieldValue = FieldValue(card = card, field = field, value = fv.value)
                fieldValueRepository.save(fieldValue)
            }
        }

        return deck
    }
}