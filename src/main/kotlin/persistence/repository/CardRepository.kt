package org.showoff.persistence.repository

import org.showoff.entity.deck.Card
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CardRepository : JpaRepository<Card, UUID> 