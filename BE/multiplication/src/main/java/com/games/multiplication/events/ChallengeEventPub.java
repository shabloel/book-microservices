package com.games.multiplication.events;

import com.games.multiplication.domain.dto.AttemptCheckedEvent;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.services.mapper.SourceDestinationMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {

    private final AmqpTemplate amqpTemplate;
    private final String challengesTopicExchange;

    private final SourceDestinationMapper sourceDestinationMapper;


    public ChallengeEventPub(AmqpTemplate amqpTemplate, @Value("${amqp.exchange.attempts}") String challengesTopicExchange, SourceDestinationMapper sourceDestinationMapper) {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchange = challengesTopicExchange;
        this.sourceDestinationMapper = sourceDestinationMapper;
    }

    public void challengeSolved(final AttemptChecked attemptChecked) {
        AttemptCheckedEvent event = buildEvent(attemptChecked);
        String routingKey = "attempt." + (event.isCorrect() ? "correct" : "wrong");
        amqpTemplate.convertAndSend(challengesTopicExchange, routingKey, event);
    }

    private AttemptCheckedEvent buildEvent(final AttemptChecked attemptChecked) {
        return sourceDestinationMapper.attempCheckedToAttemptCheckedEvent(attemptChecked);
    }
}
