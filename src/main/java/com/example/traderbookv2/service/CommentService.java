package com.example.traderbookv2.service;

import com.example.traderbookv2.model.CommentEntity;
import com.example.traderbookv2.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public UserService userService;

    public CommentEntity submitCommentIntoDB(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public ArrayList<CommentEntity> getAllCommentsForDB(String postId) {

        ArrayList<CommentEntity> commentList = commentRepository.findAllByPostId(postId);

        for (int i = 0; i < commentList.size(); i++) {
            CommentEntity commentItem = commentList.get(i);
            commentItem.setUserName(userService.displayUserMetaData(commentItem.getUserId()).getUserName());
        }

        return commentList;
    }
}
