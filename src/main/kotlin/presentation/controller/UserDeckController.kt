package org.showoff.presentation.controller

import org.showoff.application.service.UserDeckService
import org.showoff.application.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user-decks")
class UserDeckController(
    private val userDeckService: UserDeckService,
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody req: UserDeckRequest): ResponseEntity<Void> {
        val user = userService.findByEmail(req.userEmail)
            ?: return ResponseEntity.notFound().build()

        userDeckService.addUserToDeck(
            userId = user.id,
            deckId = req.deckId,
            creatorId = req.creatorId
        )

        return ResponseEntity.noContent().build()
    }
    @DeleteMapping("/register")
    fun delete(@RequestBody req: UserDeckRequest): ResponseEntity<Void> {
        val user = userService.findByEmail(req.userEmail)
            ?: return ResponseEntity.notFound().build()

        userDeckService.removeUserFromDeck(
            userId = user.id,
            deckId = req.deckId,
            creatorId = req.creatorId
        )

        return ResponseEntity.noContent().build()
    }
}

data class UserDeckRequest(val userEmail: String, val deckId: UUID, val creatorId: UUID)