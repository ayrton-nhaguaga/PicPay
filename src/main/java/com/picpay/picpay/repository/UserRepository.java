package com.picpay.picpay.repository;

import com.picpay.picpay.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByDocument(String document);

    Optional<User> findUserById(String id);
}
