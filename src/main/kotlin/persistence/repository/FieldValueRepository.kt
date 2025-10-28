package org.showoff.persistence.repository

import org.showoff.entity.deck.FieldValue
import org.showoff.entity.deck.FieldValueId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FieldValueRepository : JpaRepository<FieldValue, FieldValueId>{
    fun findAllByCardIdIn(cardIds: Collection<UUID>): List<FieldValue>
    fun deleteByCardIdIn(cardIds: Collection<UUID>)
}