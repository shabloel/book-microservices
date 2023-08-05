package com.games.gamification.gamification.testhelpers;

import com.games.gamification.gamification.domain.model.BadgeCard;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;

import java.util.Iterator;
import java.util.List;

public class CreateEntities {

    public static List<ScoreCard> createListOfScoreCards(final int i) {
        List.of(createScoreCard(true));
        return null;
    }

    public static ScoreCard createScoreCard(final boolean hasId) {
        if (hasId) {
            return new ScoreCard(1L, 1L, 1L, System.currentTimeMillis(), 10);
        } else {
            return new ScoreCard(1L, 1L);
        }
    }

    public static List<BadgeCard> createListOfBadgecards(final int i) {
        return List.of(createBadgeCard(true));
    }

    public static BadgeCard createBadgeCard(final boolean hasId) {
        if (hasId) {
            return new BadgeCard(1L, 1L, System.currentTimeMillis(), BadgeType.BRONZE);
        } else {
            return new BadgeCard(1L, BadgeType.BRONZE);
        }
    }
}
