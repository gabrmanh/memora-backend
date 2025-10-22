package org.showoff.application.dto

import org.showoff.entity.user.User
import java.util.UUID

data class UserDTO(
    val id: UUID,
    val name: String,
    val email: String
)

fun User.toDTO(): UserDTO = UserDTO(
    id = this.id,
    name = this.name,
    email = this.email
)