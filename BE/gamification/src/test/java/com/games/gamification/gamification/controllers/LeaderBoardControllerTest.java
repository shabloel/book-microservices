package com.games.gamification.gamification.controllers;

import com.games.gamification.gamification.domain.model.LeaderBoardRow;
import com.games.gamification.gamification.services.LeaderBoardService;
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

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LeaderBoardController.class)
@AutoConfigureJsonTesters
class LeaderBoardControllerTest {

    @MockBean
    private LeaderBoardService leaderBoardServiceMock;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<LeaderBoardRow>> jacksonTesterLeaderBoardRow;

    @Test
    void getLeaderBoard() throws Exception {
        //given
        LeaderBoardRow row1 = new LeaderBoardRow(1L, 49L);
        LeaderBoardRow row2 = new LeaderBoardRow(2L, 51L);
        List<LeaderBoardRow> leaderBoardRows = List.of(row1, row2);
        given(leaderBoardServiceMock.getCurrentLeaderBoard())
                .willReturn(leaderBoardRows);

        //when
        MockHttpServletResponse response = mvc.perform(
                get("/leaders")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jacksonTesterLeaderBoardRow.write(leaderBoardRows).getJson());
    }
}