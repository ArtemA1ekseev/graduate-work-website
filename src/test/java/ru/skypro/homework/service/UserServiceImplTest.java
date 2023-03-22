package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.MyUserDetails;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.security.SecurityUtils.getUserDetailsFromContext;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
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

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userMapper.createUserDtoToEntity(any(CreateUserDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto userDto2 = userService.createUser(new CreateUserDto());

        assertEquals(userDto, userDto2);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUsers() {
        List<User> users = new ArrayList<>();
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

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(anyCollection())).thenReturn(usersDto);

        List<UserDto> usersDto2 = userService.getUsers();

        assertEquals(usersDto, usersDto2);
        assertEquals(2, usersDto2.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void update() {
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

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto userDto2 = userService.updateUser(new UserDto());

        assertEquals(userDto, userDto2);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById() {
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

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto userDto2 = userService.getUserById(1L);

        assertEquals(userDto, userDto2);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUserByIdThrows() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void newPassword() {
        User testUser = new User();

        testUser.setId(1L);
        testUser.setEmail("abc@mail.ru");
        testUser.setPassword("12345");
        testUser.setRole(Role.USER);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("12345678");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(new MyUserDetails(testUser));

        userService.newPassword("12345678", "87654321");

        assertTrue(true);
    }

    @Test
    void updateRoleUser() {
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

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto userDto2 = userService.updateRole(1L, Role.USER);

        assertEquals(userDto, userDto2);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }
}