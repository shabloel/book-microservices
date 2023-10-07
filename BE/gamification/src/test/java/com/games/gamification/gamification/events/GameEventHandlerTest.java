package com.games.gamification.gamification.events;

import com.games.gamification.gamification.domain.dto.AttemptCheckedEvent;
import com.games.gamification.gamification.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameEventHandlerTest {

    private GameEventHandler classUnderTest;

    @Mock
    private GameService gameService;

    @BeforeEach
    void setUp() {
        classUnderTest = new GameEventHandler(gameService);
    }

    @Test
    void handleAttemptCheckedEvent() {

        //given
        AttemptCheckedEvent attemptCheckedEvent =
                new AttemptCheckedEvent(1L, true, 12, 12, 1L, "Henkie", 144);

        //when
        classUnderTest.handleAttemptCheckedEvent(attemptCheckedEvent);

        //then
        verify(gameService, times(1)).newAttemptFromUser(any());
    }
}