package com.games.gamification.gamification.repositories;

import com.games.gamification.gamification.domain.dto.ChallengeSolvedDto;
import com.games.gamification.gamification.domain.model.BadgeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepo extends CrudRepository<BadgeCard, Long> {

    List<BadgeCard> findByUserIdOOrderByBadgeTimestampBadgeTimestampDesc(Long id);
}
