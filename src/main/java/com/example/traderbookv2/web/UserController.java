package com.example.traderbookv2.web;

import com.example.traderbookv2.exception.ApiRequestException;
import com.example.traderbookv2.model.UserEntity;
import com.example.traderbookv2.service.MailGunService;
import com.example.traderbookv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public MailGunService mailGunService;

    @PostMapping("")
    public UserEntity submitUser(@RequestBody (required = false) UserEntity user) {
        mailGunService.sendMail("Successfully registered","Welcome to TR^DERBOOK","petko.lyutskanov@trading212.com");
        try {
            return userService.submitMetaDataOfUser(user);
        } catch (Exception e) {
            throw new ApiRequestException("Could not submit user data");
        }
    }

    @GetMapping("/{userid}")
    private UserEntity getUserDetails(@PathVariable("userid") String userId) {
        try {
            return userService.displayUserMetaData(userId);
        } catch (Exception e) {
            throw new ApiRequestException("Could not get the user. UserId missing or is wrong");
        }
    }
}
