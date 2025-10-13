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

    @Column(nullable = false)
    val type: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    val deck: Deck?
) {
    protected constructor() : this(UUID.randomUUID(), "", "", null)
}