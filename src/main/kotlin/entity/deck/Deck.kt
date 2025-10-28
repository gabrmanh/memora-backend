package org.showoff.entity.deck

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.showoff.entity.user.User
import java.util.UUID

@Suppress("ProtectedInFinal")
@Entity
@Table(name = "deck")
data class Deck(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @Column(nullable = false)
    val version: Int = 1,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User?
) {
    protected constructor() : this(UUID.randomUUID(), "", null, 1,null)
}