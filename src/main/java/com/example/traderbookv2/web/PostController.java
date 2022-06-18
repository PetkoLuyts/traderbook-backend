package com.example.traderbookv2.web;

import com.example.traderbookv2.model.PostEntity;
import com.example.traderbookv2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    public PostService postService;

    @PostMapping("")
    public PostEntity submitUserPost(@RequestBody PostEntity postEntity) {
        return postService.submitPostDataIntoDB(postEntity);
    }

    @GetMapping("")
    private ArrayList<PostEntity> getAllPosts() {
        return postService.retrievePosts();
    }
}
