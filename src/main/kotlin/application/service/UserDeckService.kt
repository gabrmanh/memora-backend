package org.showoff.application.service

import org.showoff.entity.user.UserDeck
import org.showoff.persistence.repository.DeckRepository
import org.showoff.persistence.repository.UserDeckRepository
import org.showoff.persistence.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserDeckService(
    private val userDeckRepository: UserDeckRepository,
    private val userRepository: UserRepository,
    private val deckRepository: DeckRepository
) {
    fun addUserToDeck(userId: UUID, deckId: UUID, creatorId: UUID) {
        var creatorUserDeck = userDeckRepository.findByUserIdAndDeckId(creatorId, deckId)
            ?: throw IllegalArgumentException("Creator user is not associated with the deck: $deckId")

        if (creatorUserDeck.role != "owner")
            throw IllegalArgumentException("Creator user is not owner of the deck: $deckId")

        var user = userRepository.findById(userId) ?: throw IllegalArgumentException("User not found: $userId")
        var deck = deckRepository.findById(deckId) ?: throw IllegalArgumentException("Deck not found: $deckId")

        var userDeck = UserDeck(role = "editor", user = user.get(), deck = deck.get())
        userDeckRepository.save(userDeck)
    }
}