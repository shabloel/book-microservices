package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.ChallengeSolvedDto;
import com.games.gamification.gamification.domain.model.BadgeCard;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;
import com.games.gamification.gamification.repositories.BadgeRepo;
import com.games.gamification.gamification.repositories.ScoreCardRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private final BadgeRepo badgeRepo;
    private final ScoreCardRepo scoreCardRepo;
    private final List<BadgeProcessor> badgeProcessors;

    public GameServiceImpl(BadgeRepo badgeRepo, ScoreCardRepo scoreCardRepo, List<BadgeProcessor> badgeProcessors) {
        this.badgeRepo = badgeRepo;
        this.scoreCardRepo = scoreCardRepo;
        this.badgeProcessors = badgeProcessors;
    }

    @Override
    public Optional<GameResult> newAttemptFromUser(final ChallengeSolvedDto challenge) {
        if (challenge == null) {
            return Optional.empty();
        }
        if (challenge.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(
                    challenge.getUserId(),
                    challenge.getAttemptId()
            );
            scoreCardRepo.save(scoreCard);
            log.info("Stored {} points for user {}, with attempt id {}",
                    scoreCard.getScore(),
                    challenge.getUserAlias(),
                    challenge.getAttemptId());

            List<BadgeCard> badgeCardList = processForBadges(challenge);

            return Optional.of(new GameResult(scoreCard.getScore(), badgeCardList
                    .stream()
                    .map(BadgeCard::getBadgeType)
                    .collect(Collectors.toList())));
        } else {
            log.info("Attempt id {} is not correct, user {} does not get any points.", challenge.getAttemptId(),  challenge.getUserAlias());
            return Optional.of(new GameResult(0, List.of()));
        }
    }

    private List<BadgeCard> processForBadges(ChallengeSolvedDto challenge) {

        Optional<Integer> optTotalScore = scoreCardRepo.getTotalScoreForUser(challenge.getUserId());

        if(optTotalScore.isEmpty()){
            return Collections.emptyList();
        }
        int totalScore = optTotalScore.get();

        List<ScoreCard> scoreCardList = scoreCardRepo.findByUserIdOrderByScoreTimestampDesc(challenge.getUserId());
        Set<BadgeType> alreadyGotBadges = badgeRepo
                .findByUserIdOrderByBadgeTimestampBadgeTimestampDesc(challenge.getUserId())
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, challenge))
                .flatMap(Optional::stream)
                .map(bt -> new BadgeCard(challenge.getUserId(), bt))
                .collect(Collectors.toList());

        badgeRepo.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}
