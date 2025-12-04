package org.showoff.entity.deck

import jakarta.persistence.*
import java.util.*

@Suppress("ProtectedInFinal")
@Entity
@Table(name = "field")
data class Field(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    val deck: Deck?,
    val role: String
) {
    protected constructor() : this(UUID.randomUUID(), "", null, "FRONT")
}