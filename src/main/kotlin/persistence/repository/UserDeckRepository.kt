package org.showoff.persistence.repository

import org.showoff.entity.user.UserDeck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserDeckRepository : JpaRepository<UserDeck,UUID> {
    fun findByUserIdAndDeckId(userId: UUID, deckId: UUID): UserDeck?
}