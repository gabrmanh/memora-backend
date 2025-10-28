package org.showoff.application.dto

import java.util.UUID

data class DeckSyncDTO(
    val id: UUID,
    val name: String,
    val description: String?,
    val createdById: UUID,
    val fields: List<FieldDTO>,
    val cards: List<CardDTO>
)

data class FieldDTO(
    val id: UUID,
    val name: String,
    val type: String
)

data class CardDTO(
    val id: UUID,
    val difficulty: Int,
    val index: Int,
    val fieldValues: List<FieldValueDTO>
)

data class FieldValueDTO(
    val fieldId: UUID,
    val value: String
)