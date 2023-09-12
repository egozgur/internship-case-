package com.reddlyne.suggestai.controller;


import com.reddlyne.suggestai.controller.request.UserLoginRequest;
import com.reddlyne.suggestai.controller.request.UserRegisterRequest;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.controller.response.UserRegisterResponse;
import com.reddlyne.suggestai.controller.response.eRegistrationStatus;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.User;
import com.reddlyne.suggestai.service.UserService;
import com.reddlyne.suggestai.service.exception.AuthenticationFailure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        System.out.println("received: " + userRegisterRequest.toString());
        User user = userService.registerUser(userRegisterRequest.getUsername(), userRegisterRequest.getPassword(), userRegisterRequest.getEmail());

        if (user == null) {
            throw new RegistirationNotCompleted("Something went wrong when creating user.");
        }

        UserRegisterResponse userResponse = new UserRegisterResponse(user.getLogin(), eRegistrationStatus.SUCCESS);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        try {
            UserLoginResponse userResponse = userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());
            return ResponseEntity.ok(userResponse);
        } catch (AuthenticationFailure e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
