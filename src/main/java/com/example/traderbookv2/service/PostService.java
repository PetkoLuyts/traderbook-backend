package com.example.traderbookv2.service;

import com.example.traderbookv2.model.PostEntity;
import com.example.traderbookv2.model.StatusEntity;
import com.example.traderbookv2.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserService userService;

    public PostEntity submitPostDataIntoDB(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    public ArrayList<PostEntity> retrievePosts() {

        ArrayList<PostEntity> postsList = postRepository.findAll();

        for (int i = 0; i < postsList.size(); i++) {
            PostEntity post = postsList.get(i);
            post.setUserName(userService.displayUserMetaData(post.getUserId()).getUserName());
        }

        Collections.sort(postsList, (a,b)  -> b.getId() - a.getId());

        return postsList;
    }
}
