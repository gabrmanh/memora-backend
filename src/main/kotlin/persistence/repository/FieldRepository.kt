package org.showoff.persistence.repository

import org.showoff.entity.deck.Field
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FieldRepository: JpaRepository<Field, UUID>{
    fun findAllByDeckId(deckId: UUID): List<Field>
    fun deleteByDeckId(deckId: UUID)
}