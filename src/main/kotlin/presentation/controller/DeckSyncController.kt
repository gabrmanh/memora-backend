package org.showoff.presentation.controller

import org.showoff.application.dto.DeckSyncDTO
import org.showoff.application.service.DeckSyncService
import org.showoff.entity.deck.Deck
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sync")
class DeckSyncController(
    private val deckSyncService: DeckSyncService
) {

    @PostMapping("/deck")
    fun syncDeck(@RequestBody deckDTO: DeckSyncDTO): Deck {
        return deckSyncService.syncDeck(deckDTO)
    }
}