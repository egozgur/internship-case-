package com.reddlyne.suggestai.repository;

import com.reddlyne.suggestai.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {

    Optional<UserModel> findByLoginAndPassword(String login, String password);

}
