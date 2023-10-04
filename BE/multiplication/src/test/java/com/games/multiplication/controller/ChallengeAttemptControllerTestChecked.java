package com.games.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.services.ChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
class ChallengeAttemptControllerTestChecked {

    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AttemptDTO> jsonRequestAttempt;

    @Autowired
    private JacksonTester<AttemptChecked> jsonChallengeAttempt;


    @BeforeEach
    void setUp() {
    }

    @Test
    void postValidChallengeAttempt() throws Exception {
        //given
        Uzer user = new Uzer(1L, "Henkie");

        AttemptDTO attemptDTO =
                new AttemptDTO(12, 12, "Henkie", 144);

        AttemptChecked attemptChecked =
                new AttemptChecked(1L, user, 12, 12, 144, true);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(attemptDTO);


        //when
        when(challengeService.verifyAttempt(any())).thenReturn(attemptChecked);

        MockHttpServletResponse response = mockMvc.perform(
                        post("/attempts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonChallengeAttempt.write(attemptChecked).getJson());
    }

    @Test
    void postInvalidChallengeAttempt() throws Exception {
        //given
        Uzer user = new Uzer(1L, "Henkie");

        AttemptDTO attemptDTO =
                new AttemptDTO(1200, -12, "Henkie", -150);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt
                                .write(attemptDTO)
                                .getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}