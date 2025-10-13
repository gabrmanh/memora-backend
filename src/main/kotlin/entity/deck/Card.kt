package org.showoff.entity.deck

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Suppress("ProtectedInFinal")
@Entity
@Table(name = "card")
data class Card(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val difficulty: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    val deck: Deck?
) {
    protected constructor() : this(UUID.randomUUID(), 0, null)
}