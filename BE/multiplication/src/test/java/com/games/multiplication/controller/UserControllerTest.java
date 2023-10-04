package com.games.multiplication.controller;

import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.UserRepository;
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

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    MockMvc mvc;

    @Autowired
    private JacksonTester<List<Uzer>> jacksonTester;

    @Test
    void should_get_users_by_IdList() throws Exception {
        //given
        Uzer user1 = new Uzer(1L, "Kees");
        Uzer user2 = new Uzer(2L, "Jan");
        List<Uzer> users = List.of(user1, user2);
        given(userRepository.findAllByIdIn(List.of(1L, 2L))).willReturn(users);
        //when

        MockHttpServletResponse response = mvc.perform(get("/users/1,2"))
                .andReturn().getResponse();
        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jacksonTester.write(users).getJson());
    }
}