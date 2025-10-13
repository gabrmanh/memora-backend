package org.showoff.entity.deck

import java.util.UUID

data class FieldValueId(
    var card: UUID? = null,
    var field: UUID? = null
) : java.io.Serializable