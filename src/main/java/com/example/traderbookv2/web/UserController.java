package com.example.traderbookv2.web;

import com.example.traderbookv2.model.UserEntity;
import com.example.traderbookv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping("")
    public UserEntity submitUser(@RequestBody UserEntity user) {
        return userService.submitMetaDataOfUser(user);
    }

    @GetMapping("/{userid}")
    private UserEntity getUserDetails(@PathVariable("userid") String userId) {
        return userService.displayUserMetaData(userId);
    }
}
