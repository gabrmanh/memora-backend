package org.showoff.presentation.controller

import org.showoff.application.dto.DeckSyncDTO
import org.showoff.application.service.DeckSyncService
import org.showoff.entity.deck.Deck
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sync")
class DeckSyncController(
    private val deckSyncService: DeckSyncService
) {

    @PostMapping("/deck")
    fun syncDeck(@RequestBody deckDTO: DeckSyncDTO): ResponseEntity<Deck> {
        return ResponseEntity.ok(deckSyncService.syncDeck(deckDTO))
    }

    @GetMapping("/deck")
    fun syncDeck(@RequestParam deckId: UUID): ResponseEntity<DeckSyncDTO> {
        return ResponseEntity.ok(deckSyncService.getDeckForSync(deckId))
    }
}