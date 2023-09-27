package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.LeaderBoardRow;
import com.games.gamification.gamification.repositories.BadgeRepo;
import com.games.gamification.gamification.repositories.ScoreCardRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreCardRepo scoreCardRepo;
    private final BadgeRepo badgeRepo;

    public LeaderBoardServiceImpl(ScoreCardRepo scoreCardRepo, BadgeRepo badgeRepo) {
        this.scoreCardRepo = scoreCardRepo;
        this.badgeRepo = badgeRepo;
    }


    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {

        List<LeaderBoardRow> leaderBoardRows = scoreCardRepo.findFirstTen();

        return leaderBoardRows.stream().map(row -> {
            List<String> badges =
                    badgeRepo.findByUserIdOrderByBadgeTimestampDesc(row.getUserId())
                            .stream()
                            .map(badge -> badge.getBadgeType().getDescription())
                            .toList();
            return row.withBadges(badges);
        }).toList();
    }
}
