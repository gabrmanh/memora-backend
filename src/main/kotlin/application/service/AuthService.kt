package org.showoff.application.service

import org.showoff.application.dto.UserDTO
import org.showoff.application.dto.toDTO
import org.showoff.entity.user.User
import org.showoff.persistence.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(name: String, email: String, password: String): UserDTO {
        if (userRepository.findByEmail(email) != null) {
            throw IllegalArgumentException("Email already in use")
        }

        val user = User(
            id = UUID.randomUUID(),
            name = name,
            email = email,
            passwordHash = passwordEncoder.encode(password)
        )
        userRepository.save(user)
        return user.toDTO()
    }

    fun login(email: String, password: String): UserDTO {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid credentials")

        if (!passwordEncoder.matches(password, user.passwordHash)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        return user.toDTO()
    }
}