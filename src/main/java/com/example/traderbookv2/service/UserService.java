package com.example.traderbookv2.service;

import com.example.traderbookv2.model.UserEntity;
import com.example.traderbookv2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    public UserEntity submitMetaDataOfUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity displayUserMetaData(String userId) {
        return userRepository.findByUserId(userId);
    }
}
