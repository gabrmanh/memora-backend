package org.showoff.entity.user

import jakarta.persistence.*
import java.util.*

@Suppress("ProtectedInFinal")
@Entity
@Table(name = "\"user\"")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String
) {
    protected constructor() : this(UUID.randomUUID(), "", "", "")
}