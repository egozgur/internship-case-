package com.reddlyne.suggestai.controller;

import com.reddlyne.suggestai.controller.request.UserLoginRequest;
import com.reddlyne.suggestai.controller.request.UserRegisterRequest;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.controller.response.UserRegisterResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.UserModel;
import com.reddlyne.suggestai.service.UserService;
import com.reddlyne.suggestai.service.exception.AuthenticationFailure;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
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
        UserModel userModel = userService.registerUser(userRegisterRequest.getUsername(), userRegisterRequest.getPassword(), userRegisterRequest.getMail());

        if (userModel == null) {
            throw new RegistirationNotCompleted("Something went wrong when creating user.");
        }

        UserRegisterResponse userResponse = new UserRegisterResponse();
        userResponse.setId(userModel.getId());
        userResponse.setUsername(userModel.getLogin());
        userResponse.setMail(userModel.getEmail());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserModel userModel;
        try {
            userModel = userService.authenticate(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        } catch (AuthenticationFailure e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setUserId(userModel.getId());
        return ResponseEntity.ok(userResponse);

    }
}
