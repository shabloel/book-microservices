package com.games.gamification.gamification.repositories;

import com.games.gamification.gamification.domain.model.ScoreCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ScoreCardRepo extends CrudRepository<ScoreCard, Long> {

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long id);
    Optional<Integer> getTotalScoreForUser(Long id);
}
