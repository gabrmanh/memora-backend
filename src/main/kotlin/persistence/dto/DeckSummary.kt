package org.showoff.persistence.dto

import java.util.*

data class DeckSummary(
    val id: UUID,
    val name: String,
    val description: String?,
    val cardCount: Long
)