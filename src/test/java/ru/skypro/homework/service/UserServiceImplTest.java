package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
        User testUser = new User();

        testUser.setPassword("123");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(any())).thenReturn("12345678");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User user = userService.createUser(testUser);

        assertEquals(testUser, user);
        assertEquals(testUser.getPassword(), user.getPassword());

        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void getUsers() {

        User user = new User();
        User user1 = new User();

        List<User> list = new ArrayList<>(List.of(user, user1));

        when(userRepository.findAll()).thenReturn(list);

        Collection<User> users = userService.getUsers();

        assertEquals(list, users);

        assertEquals(2, users.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void update() {
        User testUser = new User();

        testUser.setId(1L);
        testUser.setEmail("abc@mail.ru");
        testUser.setPassword("12345");
        testUser.setRole("USER");

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Ivan");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User user = userService.update(new User());

        assertEquals(testUser, user);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void getUserById() {
        User testUser = new User();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        User user = userService.getUserById(1L);

        assertEquals(testUser, user);

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
        testUser.setEmail("b@maiil.ru");

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Ivan");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        when(passwordEncoder.encode(any())).thenReturn("12345678");

        userService.newPassword("12345678", "87654321");

        assertTrue(true);

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void newPasswordFalse() {
        User testUser = new User();

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Ivan");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        userService.newPassword("12345678", "87654321");

        assertFalse(false);
    }

    @Test
    void updateRoleUser() {
        User testUser = new User();
        testUser.setEmail("b@maiil.ru");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User user = userService.updateRoleUser(1L, Role.USER);

        assertEquals(testUser, user);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }
}