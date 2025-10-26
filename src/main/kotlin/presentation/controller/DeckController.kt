package org.showoff.presentation.controller

import org.showoff.application.dto.UserDTO
import org.showoff.application.service.DeckService
import org.showoff.persistence.dto.DeckSummary
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/decks")
class DeckController(
    private val deckService: DeckService
) {

    /**
     * Get paginated list of decks with optional search filter.
     */
    @GetMapping
    fun getDecks(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<DeckSummary> {
        return deckService.getDeckSummaries(search, page, size)
    }

    /**
     * Get all users associated with a specific deck.
     */
    @GetMapping("/{deckId}/users")
    fun getUsersInDeck(
        @PathVariable deckId: UUID
    ): List<UserDTO> {
        return deckService.getUsersForDeck(deckId)
    }
}