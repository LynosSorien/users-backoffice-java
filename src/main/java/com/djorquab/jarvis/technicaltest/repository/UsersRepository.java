package com.djorquab.jarvis.technicaltest.repository;

import com.djorquab.jarvis.technicaltest.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsersRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);
    Users findByEmail(String email);

    void deleteByUsernameNot(String username);
    void deleteByUsername(String username);
    void deleteByEmail(String email);

    List<Users> findByUsernameLike(String username);
    List<Users> findByEmailLike(String email);
}
