package com.games.gamification.gamification.repositories;

import com.games.gamification.gamification.domain.model.LeaderBoardRow;
import com.games.gamification.gamification.domain.model.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreCardRepo extends CrudRepository<ScoreCard, Long> {

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long id);

    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId = :id GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("id") Long id);

    @Query("SELECT " +
            "NEW com" +
            ".games" +
            ".gamification." +
            "gamification" +
            ".domain" +
            ".model" +
            ".LeaderBoardRow(s.userId, SUM(s.score)) FROM ScoreCard s GROUP BY s.userId " +
            "ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirstTen();
}
