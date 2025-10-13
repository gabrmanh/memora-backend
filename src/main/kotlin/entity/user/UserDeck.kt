package org.showoff.entity.user

import jakarta.persistence.*
import org.showoff.entity.deck.Deck
import java.util.*

@Suppress("ProtectedInFinal")
@Entity
@Table(name = "user_deck")
data class UserDeck(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val role: String, // e.g. "owner", "editor", "viewer"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    val deck: Deck?
) {
    protected constructor() : this(UUID.randomUUID(), "viewer", null, null)
}