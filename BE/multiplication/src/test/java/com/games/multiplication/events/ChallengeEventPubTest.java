package com.games.multiplication.events;

import com.games.multiplication.domain.dto.AttemptCheckedEvent;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.services.mapper.SourceDestinationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeEventPubTest {
    private ChallengeEventPub classUnderTest;

    @Mock
    private AmqpTemplate amqpTemplate;

    @Mock
    private SourceDestinationMapper sourceDestinationMapper;

    @BeforeEach
    void setUp() {
        classUnderTest =
                new ChallengeEventPub(amqpTemplate, "topic.test", sourceDestinationMapper);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void challengeSolved(boolean correct) {
        //given
        AttemptChecked attemptChecked =
                new AttemptChecked(1L, new Uzer(1L, "Henkie"), 12, 12, 144, correct);
        AttemptCheckedEvent attemptCheckedEvent =
                new AttemptCheckedEvent(1L, 12, 12, 1L, "Henkie", correct, 144);
        given(sourceDestinationMapper.attempCheckedToAttemptCheckedEvent(any())).willReturn(attemptCheckedEvent);
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var attemptCheckedEventCaptor = ArgumentCaptor.forClass(AttemptCheckedEvent.class);

        //when
        classUnderTest.challengeSolved(attemptChecked);
        verify(amqpTemplate)
                .convertAndSend(
                        exchangeCaptor.capture(),
                        routingKeyCaptor.capture(),
                        attemptCheckedEventCaptor.capture());

        //then
        then(exchangeCaptor.getValue()).isEqualTo("topic.test");
        then(routingKeyCaptor.getValue()).isEqualTo("attempt." + (correct ? "correct" : "wrong"));
        then(attemptCheckedEventCaptor.getValue()).isEqualTo(attemptCheckedEvent);
    }
}