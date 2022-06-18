package com.example.traderbookv2.repository;

import com.example.traderbookv2.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity save(UserEntity user);

    UserEntity findByUserId(String userId);
}
