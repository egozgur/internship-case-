package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.model.UsersModel;
import com.reddlyne.suggestai.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    public UsersModel registerUser(String login, String password, String email){
        if (login != null && password !=null) {
            return null;
        }else {
            UsersModel user = new UsersModel();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            return usersRepository.save(user);
        }
    }

    public UsersModel authenticate(String login, String password){
        return usersRepository.findByLoginAndPassword(login,password).orElse(null);
    }
}
