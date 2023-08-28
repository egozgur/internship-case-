package com.reddlyne.suggestai.repository;

import com.reddlyne.suggestai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

        User findByLogin(String login);
        User findByEmail(String email);


}
