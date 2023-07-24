package com.reddlyne.suggestai.controller;
import com.reddlyne.suggestai.controller.request.UserRegisterRequest;
import com.reddlyne.suggestai.controller.response.UserRegisterResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.UserModel;
import com.reddlyne.suggestai.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UsersController {
    private final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){

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
    public String login(){


        //TODO: 21.07.2023 token oluşturma yapılacak.
        UserRegisterResponse userResponse = new UserRegisterResponse();
        //userResponse.setToken("asd123");
        return null;
    }

}
