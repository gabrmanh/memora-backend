package org.showoff.persistence.repository

import org.showoff.entity.deck.FieldValue
import org.showoff.entity.deck.FieldValueId
import org.springframework.data.jpa.repository.JpaRepository

interface FieldValueRepository : JpaRepository<FieldValue, FieldValueId>