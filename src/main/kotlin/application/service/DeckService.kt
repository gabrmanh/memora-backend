package org.showoff.application.service

import org.showoff.application.dto.UserDTO
import org.showoff.application.dto.toDTO
import org.showoff.entity.deck.Deck
import org.showoff.persistence.dto.DeckSummary
import org.showoff.persistence.repository.DeckRepository
import org.showoff.persistence.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeckService(
    private val deckRepository: DeckRepository,
    private val userRepository: UserRepository
) {

    fun getDeckSummaries(search: String?, page: Int, size: Int): Page<DeckSummary> {
        val pageable = PageRequest.of(page, size)
        return deckRepository.findDeckSummaries(search, pageable)
    }

    fun getUsersForDeck(deckId: UUID): List<UserDTO> {
        val deck: Deck = deckRepository.findById(deckId)
            .orElseThrow { IllegalArgumentException("Deck not found: $deckId") }

        return userRepository.findAllByDeck(deck).map { it.toDTO() }
    }
}