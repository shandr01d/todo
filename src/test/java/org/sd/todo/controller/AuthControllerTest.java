package org.sd.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.sd.todo.dto.payload.auth.response.LoginResponseDto;
import org.sd.todo.dto.payload.todo.request.TodoRequestDto;
import org.sd.todo.dto.payload.todo.response.TodoResponseDto;
import org.sd.todo.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldLoginUser() throws Exception {
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"admin\",\"password\":\"adminpass\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect( jsonPath("$.id", is(1)) )
                .andExpect( jsonPath("$.username", is("admin")) )
                .andExpect( jsonPath("$.email", is("admin@todo.com")) )
                .andExpect( jsonPath("$.roles").isArray() )
                .andExpect( jsonPath("$.accessToken", isA(String.class)) )
                .andExpect( jsonPath("$.tokenType", is("Bearer")) )
                .andExpect( jsonPath("$.password").doesNotExist() );
    }

    @ParameterizedTest(name = "#{index} - Run login negative test = {0}")
    @MethodSource("invalidLoginRequestProvider")
    public void shouldReturnErrorOnLogin(String requestJson) throws Exception {
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().is4xxClientError());
    }

    static Stream<String> invalidLoginRequestProvider() {
        return Stream.of(
                "{\"username\":\"wrong_login\",\"password\":\"wrongpass\"}",
                "{\"username\":\"wrong_login\",\"password\":\"adminpass\"}",
                "{\"username\":\"admin\",\"password\":\"wrongpass\"}",
                "{\"username\":\"admin\",\"password\":\"\"}",
                "{\"username\":\"\",\"password\":\"adminpass\"}",
                "{\"username\":\"admin\"}",
                "{\"password\":\"adminpass\"}",
                "{\"password\":\"adminpass\"}",
                "{\"random\":\"admin\"}",
                "{\"username\":\"key\"}",
                "{}"
        );
    }

    @Test
    public void shouldSignUpUser() throws Exception {
        String jsonData =
            "{" +
                "\"username\": \"test\"," +
                "\"password\": \"testpass\"," +
                "\"email\": \"test@todo.com\"," +
                "\"roles\": [\"ROLE_USER\"]" +
            "}";
        mockMvc.perform(post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(status().is2xxSuccessful())
                .andExpect( jsonPath("$.id", is(3)) )
                .andExpect( jsonPath("$.username", is("test")) )
                .andExpect( jsonPath("$.email", is("test@todo.com")) );
    }

    @ParameterizedTest(name = "#{index} - Run signup negative test = {0}")
    @MethodSource("invalidSignUpRequestProvider")
    public void shouldReturnErrorOnSignIn(String requestJson) throws Exception {
        mockMvc.perform(post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().is4xxClientError());
    }

    static Stream<String> invalidSignUpRequestProvider() {
        return Stream.of(
                "{\"username\":\"admin\",\"password\":\"adminpass\",\"email\":\"admin@todo.com\",\"roles\":[\"ROLE_ADMIN\"]}",
                "{\"username\":\"user\",\"password\":\"userpass\",\"email\":\"user@todo.com\",\"roles\":[\"ROLE_ADMIN\"]}",
                "{\"username\":\"newuser\",\"password\":\"newpass\",\"email\":\"new@todo.com\",\"roles\":[\"WRONG_ROLE\"]}",
                "{\"username\":\"user\",\"password\":\"userpass\",\"email\":\"user@todo.com\"}",
                "{\"username\":\"user\",\"password\":\"userpass\",\"email\":\"notemail\"}",
                "{\"username\":\"newuser\",\"password\":\"shrt\",\"email\":\"user@todo.com\"}"
        );
    }
}
