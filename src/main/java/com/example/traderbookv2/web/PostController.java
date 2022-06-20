package com.example.traderbookv2.web;

import com.example.traderbookv2.exception.ApiRequestException;
import com.example.traderbookv2.model.PostEntity;
import com.example.traderbookv2.model.ShareEntity;
import com.example.traderbookv2.service.PostService;
import com.example.traderbookv2.service.ThirdPartyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    public PostService postService;

    @Autowired
    public ThirdPartyApiService thirdPartyApiService;

    @PostMapping("")
    public PostEntity submitUserPost(@RequestBody(required = false) PostEntity postEntity) {
        try {
            return postService.submitPostDataIntoDB(postEntity);
        } catch (Exception e) {
            throw new ApiRequestException("Could not submit post data");
        }
    }

    @GetMapping("")
    private ArrayList<PostEntity> getAllPosts() {
        try {
            return postService.retrievePosts();
        } catch (Exception e) {
            throw new ApiRequestException("Could not get the posts");
        }
    }

    @GetMapping("/share/{shareName}")
    private ShareEntity getShareEntityFromAPI(@PathVariable String shareName) {
        return thirdPartyApiService.coin(shareName);
    }
}
