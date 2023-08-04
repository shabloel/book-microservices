package com.games.gamification.gamification.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum BadgeType {
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    FIRST_WON("First time"),
    LUCKY_NUMBER("Lucky number");

    private final String description;
}
