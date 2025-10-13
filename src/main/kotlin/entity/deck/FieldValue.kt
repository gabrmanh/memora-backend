package org.showoff.entity.deck

import jakarta.persistence.*

@Suppress("ProtectedInFinal")
@Entity
@IdClass(FieldValueId::class)
@Table(name = "field_value")
data class FieldValue(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    val card: Card?,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    val field: Field?,

    @Column(nullable = false, length = 1200)
    val value: String
) {
    protected constructor() : this(null, null, "")
}