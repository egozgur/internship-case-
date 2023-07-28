package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.UserModel;
import com.reddlyne.suggestai.repository.UserRepository;
import com.reddlyne.suggestai.service.exception.AuthenticationFailure;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserModel registerUser(String login, String password, String mail){
        if (login == null || password ==null) {
            throw new RegistirationNotCompleted("Username or Password not valid.");
        }
        UserModel user = new UserModel();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(mail);

        UserModel userFromDb = userRepository.save(user);

        return userFromDb;
    }

    public UserModel authenticate(String login, String password){
        UserModel userModel = userRepository
                .findByLoginAndPassword(login, password)
                .orElseThrow(() -> new AuthenticationFailure("Username or password not found."));
        return userModel;
    }
}
