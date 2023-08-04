package com.games.gamification.gamification.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeCard {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @EqualsAndHashCode.Exclude
    private long badgeTimestamp;

    private BadgeType badgeType;

    public BadgeCard(final Long userId, final BadgeType badgeType){
        this(null, userId, System.currentTimeMillis(), badgeType);
    }
}
