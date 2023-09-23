package com.games.multiplication.controller;

import com.games.multiplication.domain.model.Challenge;
import com.games.multiplication.services.ChallengeGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ChallengeController.class)
@AutoConfigureJsonTesters
class ChallengeControllerTest {

    @MockBean
    private ChallengeGeneratorService challengeGeneratorService;

    @Autowired
    MockMvc mvc;

    @Autowired
    JacksonTester<Challenge> jacksonTester;

    @Test
    void should_get_challenge() throws Exception {
        //given
        Challenge randomChallenge = new Challenge(12, 12);
        given(challengeGeneratorService.randomChallenge()).willReturn(randomChallenge);

        //when
        MockHttpServletResponse response = mvc.perform(get("/challenges/random")).andReturn().getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jacksonTester.write(randomChallenge).getJson());
    }
}