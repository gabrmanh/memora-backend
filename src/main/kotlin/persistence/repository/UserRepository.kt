package org.showoff.persistence.repository

import org.showoff.entity.deck.Deck
import org.showoff.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String): User?

    @Query("""
        SELECT ud.user 
        FROM  UserDeck ud 
        WHERE ud.deck = :deck
    """)
    fun findAllByDeck(deck: Deck): List<User>
}