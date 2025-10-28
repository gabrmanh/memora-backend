package org.showoff.presentation.controller

import org.showoff.application.dto.UserDTO
import org.showoff.application.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @PostMapping("/sync")
    fun syncUserData(
        @RequestParam userId: UUID,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) email: String?
    ): ResponseEntity<UserDTO> {
        return ResponseEntity.ok(userService.syncUserData(userId, name, email))
    }
}