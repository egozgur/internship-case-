package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.configuration.jwt.JwtTokenFilter;
import com.reddlyne.suggestai.configuration.jwt.JwtTokenUtil;
import com.reddlyne.suggestai.controller.UserController;
import com.reddlyne.suggestai.controller.request.UserRegisterRequest;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.User;
import com.reddlyne.suggestai.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserController userController;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtUtil;

    private JwtTokenFilter jwtTokenFilter;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        jwtUtil = Mockito.mock(JwtTokenUtil.class);
        userController = new UserController(userService);

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

    @Test
    public void testLoginUserNotFound() {
        // Test case setup
        String login = "nonExistentUser";
        String password = "testPassword";

        when(userRepository.findByLogin(login)).thenReturn(null);

        // Execute
        UserLoginResponse loginResponse = userService.login(login, password);

        // Assert
        assertNotNull(loginResponse);
        assertNull(loginResponse.getLogin());
        assertNull(loginResponse.getJwt());


        verify(userRepository, times(1)).findByLogin(login);
        verify(passwordEncoder, never()).matches(password, "");
    }

    @Test
    public void testLoginIncorrectPassword() {
        String login = "testUser";
        String password = "wrongPassword";
        String encryptedPassword = "encryptedPassword";

        User user = new User();
        user.setLogin(login);
        user.setPassword(encryptedPassword);

        when(userRepository.findByLogin(login)).thenReturn(user);
        when(passwordEncoder.matches(password, encryptedPassword)).thenReturn(false);

        UserLoginResponse loginResponse = userService.login(login, password);

        assertNotNull(loginResponse);
        assertNull(loginResponse.getLogin());
        assertNull(loginResponse.getJwt());

        verify(userRepository, times(1)).findByLogin(login);
        verify(passwordEncoder, times(1)).matches(password, encryptedPassword);
    }

    @Test
    public void testDuplicateUsernameOrEmail() {
        String login = "existingUser";
        String password = "testPassword";
        String email = "existing@example.com";

        when(userRepository.findByLogin(login)).thenReturn(new User());

        UserRegisterRequest registerRequest = new UserRegisterRequest(login, password, email);

        // Execute
        try {
            userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
            fail("Expected RegistirationNotCompleted exception but none was thrown.");
        } catch (RegistirationNotCompleted e) {
            // Assert
            assertEquals("Username or Password not valid.", e.getMessage());
        }

        // Verify that the findByLogin method was called
        verify(userRepository, times(1)).findByLogin(login);
        verify(userRepository, never()).findByEmail(anyString()); // Assuming you have a findByEmail method
        verify(userRepository, never()).save(any(User.class));
    }
}
