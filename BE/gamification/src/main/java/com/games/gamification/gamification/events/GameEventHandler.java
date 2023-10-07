package com.games.gamification.gamification.events;

import com.games.gamification.gamification.domain.dto.AttemptCheckedEvent;
import com.games.gamification.gamification.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class GameEventHandler {

    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    void handleAttemptCheckedEvent(final AttemptCheckedEvent event) {
        log.info("Attempt received with attempt id {}", event.getId());

        try {
            gameService.newAttemptFromUser(event);
        } catch (final Exception e) {
            log.error("Error while trying to proces attempt event.", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
