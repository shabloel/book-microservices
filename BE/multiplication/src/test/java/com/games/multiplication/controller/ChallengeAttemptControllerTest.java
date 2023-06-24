package com.games.multiplication.controller;

import com.games.multiplication.challenges.ChallengeAttempt;
import com.games.multiplication.challenges.ChallengeAttemptDTO;
import com.games.multiplication.services.ChallengeService;
import com.games.multiplication.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
class ChallengeAttemptControllerTest {

    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;

    @Autowired
    private JacksonTester<ChallengeAttempt> jsonChallengeAttempt;


    @BeforeEach
    void setUp() {
    }

    @Test
    void postValidChallengeAttempt() throws Exception {
        //given
        User user = new User(1L, "Henkie");

        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(12, 12, "Henkie", 144);

        ChallengeAttempt challengeAttempt =
                new ChallengeAttempt(1L, user, 12, 12, 144, true);

        //when
        when(challengeService.verifyAttempt(any())).thenReturn(challengeAttempt);

        MockHttpServletResponse response = mockMvc.perform(post("/attempts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestAttempt
                        .write(challengeAttemptDTO)
                        .getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonChallengeAttempt.write(challengeAttempt).getJson());
    }

    @Test
    void postInvalidChallengeAttempt() throws Exception {
        //given
        User user = new User(1L, "Henkie");

        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(1200, -12, "Henkie", -150);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt
                                .write(challengeAttemptDTO)
                                .getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}