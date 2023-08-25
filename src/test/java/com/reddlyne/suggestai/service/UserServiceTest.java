package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.configuration.jwt.JwtTokenFilter;
import com.reddlyne.suggestai.configuration.jwt.JwtTokenUtil;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.model.User;
import com.reddlyne.suggestai.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtUtil;

    private JwtTokenFilter jwtTokenFilter;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        jwtUtil = Mockito.mock(JwtTokenUtil.class);

        userService = new UserService(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    public void testCreateUser() {
        String login = "testUser";
        String password = "testPassword";
        String email = "test@example.com";

        when(passwordEncoder.encode(password)).thenReturn("encryptedPassword");

        User savedUser = new User();
        savedUser.setLogin(login);
        savedUser.setPassword("encryptedPassword");
        savedUser.setEmail(email);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User registeredUser = userService.registerUser(login, password, email);

        assertNotNull(registeredUser);
        assertEquals(login, registeredUser.getLogin());
        assertEquals(email, registeredUser.getEmail());

        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    public void testLoginSuccessful() {
        String login = "testUser";
        String password = "testPassword";
        String encryptedPassword = "encryptedPassword";

        User user = new User();
        user.setLogin(login);
        user.setPassword(encryptedPassword);

        when(userRepository.findByLogin(login)).thenReturn(user);

        when(passwordEncoder.matches(password, encryptedPassword)).thenReturn(true);

        UserLoginResponse loginResponse = userService.login(login, password);

        assertNotNull(loginResponse);
        assertEquals(login, loginResponse.getLogin());

        verify(userRepository, times(1)).findByLogin(login);
        verify(passwordEncoder, times(1)).matches(password, encryptedPassword);
    }




}
