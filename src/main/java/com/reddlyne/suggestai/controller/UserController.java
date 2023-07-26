package com.reddlyne.suggestai.controller;

import com.reddlyne.suggestai.configuration.TokenManager;
import com.reddlyne.suggestai.controller.request.UserLoginRequest;
import com.reddlyne.suggestai.controller.request.UserRegisterRequest;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.controller.response.UserRegisterResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.UserModel;
import com.reddlyne.suggestai.service.UserService;
import com.reddlyne.suggestai.service.exception.AuthenticationFailure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    private final TokenManager tokenManager;

    public UserController(UserService userService, TokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {

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
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {

        System.out.println("received: " + userLoginRequest.toString());
        try {
            UserModel userModel = userService.authenticate(userLoginRequest.getUsername(), userLoginRequest.getPassword());

            UserLoginResponse userResponse = new UserLoginResponse();
            String jwt = tokenManager.generateToken(userModel.getLogin());
            userResponse.setJwt(jwt);
            return ResponseEntity.ok(userResponse);

        } catch (AuthenticationFailure e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }
}
