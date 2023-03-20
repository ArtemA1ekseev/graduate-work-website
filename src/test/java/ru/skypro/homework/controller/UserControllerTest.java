package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {


    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserMapper userMapper;


    @Autowired
    UserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void addUser() throws Exception {

        User user = new User();
        CreateUserDto createUserDto = new CreateUserDto();

        user.setEmail("a@mail.ru");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPassword("12345678");
        user.setPhone("+79991254698");

        createUserDto.setEmail("a@mail.ru");
        createUserDto.setFirstName("Ivan");
        createUserDto.setLastName("Ivanov");
        createUserDto.setPassword("12345678");
        createUserDto.setPhone("+79991254698");

        when(userService.createUser(any())).thenReturn(user);
        when(userMapper.toCreateUserDto(any())).thenReturn(createUserDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/users").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(createUserDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    void getUsers() throws Exception {

        Collection<User> users = new ArrayList<>();

        User user1 = new User();
        User user2 = new User();

        user1.setEmail("a@mail.ru");
        user1.setFirstName("Ivan");
        user1.setLastName("Ivanov");
        user1.setPhone("+79991254698");

        user2.setEmail("b@mail.ru");
        user2.setFirstName("Ivan2");
        user2.setLastName("Ivanov2");
        user2.setPhone("+79991254699");

        users.add(user1);
        users.add(user2);

        List<UserDto> usersDto = new ArrayList<>();

        UserDto userDto1 = new UserDto();
        UserDto userDto2 = new UserDto();

        userDto1.setEmail("a@mail.ru");
        userDto1.setFirstName("Ivan");
        userDto1.setLastName("Ivanov");
        userDto1.setPhone("+79991254698");

        userDto2.setEmail("b@mail.ru");
        userDto2.setFirstName("Ivan2");
        userDto2.setLastName("Ivanov2");
        userDto2.setPhone("+79991254699");

        usersDto.add(userDto1);
        usersDto.add(userDto2);

        when(userService.getUsers()).thenReturn(users);

        when(userMapper.toDto(anyCollection())).thenReturn(usersDto);


        mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results[0]").value(usersDto.get(0)))
                .andExpect(jsonPath("$.results[1]").value(usersDto.get(1)))
                .andExpect(jsonPath("$.results[0].email").value(userDto1.getEmail()))
                .andExpect(jsonPath("$.results[1].email").value(userDto2.getEmail()));
    }

    @Test
    void update() throws Exception {
        User user = new User();
        UserDto userDto = new UserDto();

        user.setEmail("a@mail.ru");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPhone("+79991254698");

        userDto.setEmail("a@mail.ru");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+79991254698");

        when(userService.updateUser(any())).thenReturn(user);
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);


        mockMvc.perform(MockMvcRequestBuilders.patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    void setPassword() throws Exception {

        NewPasswordDto passwordDto = new NewPasswordDto();
        passwordDto.setCurrentPassword("12345678");
        passwordDto.setNewPassword("87654321");

        doNothing().when(userService).newPassword(anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPassword").value(passwordDto.getCurrentPassword()))
                .andExpect(jsonPath("$.newPassword").value(passwordDto.getNewPassword()));
    }

    @Test
    void getUser() throws Exception {
        User user = new User();

        user.setId(1L);
        user.setEmail("a@mail.ru");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPassword("12345678");
        user.setPhone("+79991254698");

        UserDto userDto = new UserDto();

        userDto.setId(1);
        userDto.setEmail("a@mail.ru");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+79991254698");

        when(userService.getUserById(anyLong())).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);


        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.phone").value(userDto.getPhone()));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void updateRoleUser() throws Exception {

        User user = new User();

        user.setId(1L);
        user.setEmail("a@mail.ru");
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPassword("12345678");
        user.setPhone("+79991254698");
        user.setRole(Role.ADMIN);


        UserDto userDto = new UserDto();

        userDto.setId(1);
        userDto.setEmail("a@mail.ru");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+79991254698");


        when(userService.updateRole(anyLong(), any(Role.class))).thenReturn(user);

        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1/updateRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user.getRole()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}