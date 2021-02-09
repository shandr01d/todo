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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles("test")
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private String token;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeAll
    public void setUp() throws Exception{
        if(token == null){
            MvcResult result = mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"admin\",\"password\":\"adminpass\"}"))
                    .andReturn();

            LoginResponseDto loginResponseDto = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), LoginResponseDto.class);
            token = "Bearer " + loginResponseDto.getAccessToken();
        }
    }

    @Test
    public void shouldReturnUnauthorizedResponse() throws Exception {
        mockMvc.perform(get("/api/todos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnOneTodoResponse() throws Exception {
        Long todoId = 7L;

        mockMvc.perform(get("/api/todos/{id}", todoId.intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andExpect( jsonPath("$.id", isA(Integer.class)) )
                .andExpect( jsonPath("$.title", isA(String.class)) )
                .andExpect( jsonPath("$.dueDate", isA(String.class)) )
                .andExpect( jsonPath("$.status", is(Todo.Status.DONE.name())) );
    }

    @Test
    public void shouldReturnNotFoundResponseAsTodoBelongsToOtherUser() throws Exception {
        Long todoId = 1L;
        mockMvc.perform(get("/api/todos/{id}", todoId.intValue())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateTodo() throws Exception {
        String todoTitle = "some title";
        Date dueDate = new Date();
        String todoStatus = Todo.Status.CREATED.name();
        String requestJson = dtoToJsonContent(new TodoRequestDto(todoTitle, dueDate, todoStatus));

        mockMvc.perform(post("/api/todos")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect( jsonPath("$.id", isA(Integer.class)) )
                .andExpect( jsonPath("$.title", is(todoTitle)) )
                .andExpect( jsonPath("$.dueDate", is(dateFormat.format(dueDate))) )
                .andExpect( jsonPath("$.status", is(todoStatus)) )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTodoListFilteredByDateResponse() throws Exception {

        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dueDate", "2021-01-11")
        )
                .andExpect( jsonPath("$" ).isArray() )
                .andExpect( jsonPath("$[*].id", everyItem(isA(Integer.class))) )
                .andExpect( jsonPath("$[*].title", everyItem(isA(String.class))) )
                .andExpect( jsonPath("$[*].dueDate", everyItem(isA(String.class))) )
                .andExpect( jsonPath("$[*].status", everyItem(isA(String.class))) )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTodoListFilteredByDateAndStatusResponse() throws Exception {
        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dueDate", "2021-01-11")
                .param("status", Todo.Status.CREATED.name())
        )
                .andExpect( jsonPath("$" ).isArray() )
                .andExpect( jsonPath("$[*].id", everyItem(isA(Integer.class))) )
                .andExpect( jsonPath("$[*].title", everyItem(isA(String.class))) )
                .andExpect( jsonPath("$[*].dueDate", everyItem(isA(String.class))) )
                .andExpect( jsonPath("$[*].status", everyItem(isA(String.class))) )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTodoListFilteredByDateAndStatusAndTitleResponse() throws Exception {
        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dueDate", "2021-01-13")
                .param("status", Todo.Status.DONE.name())
        )
                .andExpect( jsonPath("$" ).isArray() )
                .andExpect( jsonPath("$[*].id", everyItem(isA(Integer.class))) )
                .andExpect( jsonPath("$[*].title", everyItem(isA(String.class))) )
                .andExpect( jsonPath("$[*].dueDate", everyItem(isA(String.class))) )
                .andExpect( jsonPath("$[*].status", everyItem(isA(String.class))) )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTodayListResponse() throws Exception {
        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andExpect( jsonPath("$" ).isArray());
    }

    @ParameterizedTest(name = "#{index} - Run negative test = {0}")
    @MethodSource("invalidRequestProvider")
    public void shouldReturnErrorOnTodoCreation(String requestJson) throws Exception {
        mockMvc.perform(post("/api/todos")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest(name = "#{index} - Run update test = {0}")
    @MethodSource("validUpdateRequestProvider")
    @Rollback(true)
    public void shouldUpdateTodo(TodoTestRequestResponseData todoTestRequestResponseData) throws Exception {
        Integer todoId = 7;
        TodoResponseDto todoResponseDto = todoTestRequestResponseData.expectedResponseDto;

        mockMvc.perform(put("/api/todos/{id}", todoId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoTestRequestResponseData.requestData)
        )
                .andExpect( jsonPath("$.id", is(todoResponseDto.getId().intValue())) )
                .andExpect( jsonPath("$.title", is(todoResponseDto.getTitle())) )
                .andExpect( jsonPath("$.dueDate", is(dateFormat.format(todoResponseDto.getDueDate()))) )
                .andExpect( jsonPath("$.status", isA(String.class)) )
                .andExpect(status().isOk());
    }

    static Stream<TodoTestRequestResponseData> validUpdateRequestProvider() throws ParseException {
        Long originalId = 7L;
        String originalTitle = "some old and completed task";
        String originalStatus = Todo.Status.DONE.name();
        String originalDate = "2021-01-01";

        return Stream.of(
                new TodoTestRequestResponseData(
                        "{\"title\": \"updated title\", \"dueDate\": \""+originalDate+"\", \"status\": \""+originalStatus+"\"}",
                        new TodoResponseDto(originalId, "updated title", originalDate, originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"dueDate\": \"2021-01-27\", \"status\": \""+originalStatus+"\"}",
                        new TodoResponseDto(originalId, originalTitle, "2021-01-27", originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"dueDate\": \""+originalDate+"\", \"status\": \"DELETED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.DELETED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"dueDate\": \""+originalDate+"\", \"status\": \"CREATED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.CREATED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"dueDate\": \""+originalDate+"\", \"status\": \"FAILED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.FAILED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"dueDate\": \""+originalDate+"\", \"status\": \"CANCELLED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.CANCELLED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"updated title\", \"dueDate\": \""+originalDate+"\"}",
                        new TodoResponseDto(originalId, "updated title", originalDate, originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"dueDate\": \"2021-01-27\"}",
                        new TodoResponseDto(originalId, originalTitle, "2021-01-27", originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"updated title\", \"status\": \"CREATED\"}",
                        new TodoResponseDto(originalId, "updated title", originalDate, Todo.Status.CREATED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"dueDate\": \"2021-01-27\", \"status\": \"CREATED\"}",
                        new TodoResponseDto(originalId, originalTitle, "2021-01-27", Todo.Status.CREATED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \""+originalTitle+"\", \"status\": \"DONE\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.DONE.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"dueDate\": \"2021-01-12\", \"status\": \"DELETED\"}",
                        new TodoResponseDto(originalId, originalTitle, "2021-01-12", Todo.Status.DELETED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"some title\", \"status\": \""+originalStatus+"\"}",
                        new TodoResponseDto(originalId, "some title", originalDate, originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"updated title\", \"status\": \"CREATED\"}",
                        new TodoResponseDto(originalId, "updated title", originalDate, Todo.Status.CREATED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"some title\", \"status\": \"DELETED\"}",
                        new TodoResponseDto(originalId, "some title", originalDate, Todo.Status.DELETED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"some title\", \"status\": \"CANCELLED\"}",
                        new TodoResponseDto(originalId, "some title", originalDate, Todo.Status.CANCELLED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"some title\", \"status\": \"FAILED\"}",
                        new TodoResponseDto(originalId, "some title", originalDate, Todo.Status.FAILED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"title\": \"updated title\"}",
                        new TodoResponseDto(originalId, "updated title", originalDate, originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"dueDate\": \"2021-01-12\"}",
                        new TodoResponseDto(originalId, originalTitle, "2021-01-12", originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"status\": \""+originalStatus+"\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, originalStatus)
                ),
                new TodoTestRequestResponseData(
                        "{\"status\": \"CREATED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.CREATED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"status\": \"FAILED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.FAILED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"status\": \"DELETED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.DELETED.name())
                ),
                new TodoTestRequestResponseData(
                        "{\"status\": \"CANCELLED\"}",
                        new TodoResponseDto(originalId, originalTitle, originalDate, Todo.Status.CANCELLED.name())
                )
        );
    }

    @ParameterizedTest(name = "#{index} - Negative run update test = {0}")
    @MethodSource("invalidRequestProvider")
    public void shouldNotUpdateTodo(String requestData) throws Exception {
        Integer todoId = 7;
        mockMvc.perform(put("/api/todos/{id}", todoId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData)
        )
                .andExpect(status().is4xxClientError());
    }

    static Stream<String> invalidRequestProvider() throws ParseException {
        String tooLongTitle = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit.";

        return Stream.of(
                "{\"title\": \"\", \"dueDate\": \"\", \"status\": \"\"}",
                "{\"title\": \"to\", \"dueDate\": \"\", \"status\": \"\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"\", \"status\": \"\"}",
                "{\"title\": \"some title\", \"dueDate\": \"\", \"status\": \"\"}",
                "{\"title\": \"some title\", \"dueDate\": \"invalid date\", \"status\": \"\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"invalid date\", \"status\": \"\"}",
                "{\"title\": \"some title\", \"dueDate\": \"2021-01-25\", \"status\": \"\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"2021-01-25\", \"status\": \"\"}",
                "{\"title\": \"some title\", \"dueDate\": \"2021-01-25\", \"status\": \"2\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"2021-01-25\", \"status\": \"2\"}",
                "{\"title\": \"some title\", \"dueDate\": \"2021-01-25\", \"status\": \"wrong status\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"2021-01-25\", \"status\": \"wrong status\"}",
                "{\"title\": \"\", \"dueDate\": \"\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"\"}",
                "{\"title\": \"to\", \"dueDate\": \"\"}",
                "{\"title\": \"to\", \"dueDate\": \"invalid date\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"invalid date\"}",
                "{\"title\": \"to\", \"dueDate\": \"DELETED\"}",
                "{\"title\": \""+ tooLongTitle +"\", \"dueDate\": \"DELETED\"}",
                "{\"title\": \"\", \"status\": \"\"}",
                "{\"title\": \"to\", \"status\": \"\"}",
                "{\"title\": \"to\", \"dueDate\": \"2\"}",
                "{\"title\": \"to\", \"status\": \"wrong status\"}",
                "{\"title\": \"some title\", \"dueDate\": \"invalid date\"}",
                "{\"title\": \"some title\", \"status\": \"\"}",
                "{\"title\": \"some title\", \"status\": \"2\"}",
                "{\"title\": \"some title\", \"status\": \"wrong status\"}",
                "{\"title\": \"\"}",
                "{\"title\": \" \"}",
                "{\"title\": \"sh\"}",
                "\"dueDate\": \"invalid date\"}",
                "\"dueDate\": \"invalid date\", \"status\": \"DELETED\"}",
                "{\"status\": \"\"}",
                "{\"status\": \"2\"}",
                "{\"status\": \"wrong status\"}"
        );
    }

    private String dtoToJsonContent(TodoRequestDto todoRequestDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(todoRequestDto);
    }

    static class TodoTestRequestResponseData {
        public String requestData;
        public TodoResponseDto expectedResponseDto;

        TodoTestRequestResponseData(String requestData, TodoResponseDto expectedResponseDto){
            this.requestData = requestData;
            this.expectedResponseDto = expectedResponseDto;
        }
    }

    @Test
    public void shouldDeleteTodo() throws Exception {
        mockMvc.perform(delete("/api/todos/7")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andExpect( status().is2xxSuccessful());
    }

    @ParameterizedTest(name = "#{index} - Negative run update test = {0}")
    @MethodSource("notAllowedToDeleteIds")
    public void shouldNotDeleteTodo(Integer id) throws Exception {
        mockMvc.perform(delete("/api/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andExpect( status().is4xxClientError());
    }

    static Stream<Integer> notAllowedToDeleteIds() {
        return Stream.of(0, 1);
    }
}
