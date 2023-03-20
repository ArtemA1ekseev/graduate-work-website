package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;
    @MockBean
    private AuthService authService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    AuthControllerTest(WebApplicationContext webApplicationContext, ObjectMapper objectMapper) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.objectMapper = objectMapper;
    }

    @Test
    void login() throws Exception {
        doNothing().when(authService).login(anyString(), anyString());

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"password\",\"username\":\"user@mail.ru\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void register() throws Exception {
        doNothing().when(authService).register(any());

        RegisterReqDto registerReqDto = new RegisterReqDto();
        registerReqDto.setFirstName("name1");
        registerReqDto.setLastName("name2");
        registerReqDto.setPassword("password");
        registerReqDto.setPhone("+79995552233");
        registerReqDto.setUsername("user@mail.ru");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReqDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}