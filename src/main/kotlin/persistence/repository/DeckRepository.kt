package org.showoff.persistence.repository

import org.showoff.entity.deck.Deck
import org.showoff.persistence.dto.DeckSummary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DeckRepository : JpaRepository<Deck, UUID> {

    @Query(
        """
    SELECT new org.showoff.persistence.dto.DeckSummary(
        d.id, d.name, d.description, COUNT(c.id)
    )
    FROM Deck d
    LEFT JOIN Card c ON c.deck = d
    WHERE (
        :search IS NULL
        OR LOWER(d.name) LIKE CONCAT('%', CAST(:search AS string), '%')
        OR LOWER(COALESCE(d.description, '')) LIKE CONCAT('%', CAST(:search AS string), '%')
    )
    GROUP BY d.id, d.name, d.description
    """
    )
    fun findDeckSummaries(
        @Param("search") search: String?,
        pageable: Pageable
    ): Page<DeckSummary>

}