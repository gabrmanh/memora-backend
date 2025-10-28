package org.showoff.presentation.controller

import org.showoff.application.dto.UserDTO
import org.showoff.application.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<UserDTO> {
        val user = authService.register(req.name, req.email, req.password)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<UserDTO> {
        val user = authService.login(req.email, req.password)
        return ResponseEntity.ok(user)
    }
}

data class RegisterRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)