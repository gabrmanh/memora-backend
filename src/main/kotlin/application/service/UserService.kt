package org.showoff.application.service

import org.showoff.application.dto.UserDTO
import org.showoff.application.dto.toDTO
import org.showoff.persistence.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(    
    private val userRepository: UserRepository
) {
    fun syncUserData(userId: UUID, name: String?, email: String?): UserDTO {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("User not found: $userId")
        }

        val updatedUser = user.copy(
            name = name ?: user.name,
            email = email ?: user.email
        )

        return userRepository.save(updatedUser).toDTO()
    }
}