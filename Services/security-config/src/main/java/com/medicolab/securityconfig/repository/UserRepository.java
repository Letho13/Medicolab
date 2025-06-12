package com.medicolab.securityconfig.repository;

import com.medicolab.securityconfig.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Long> {

    Optional<User> findByUsername(String username);
}
