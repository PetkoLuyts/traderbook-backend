package com.example.traderbookv2.web;

import com.example.traderbookv2.exception.ApiRequestException;
import com.example.traderbookv2.model.CommentEntity;
import com.example.traderbookv2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    public CommentService commentService;

    @PostMapping("")
    public CommentEntity submitComment(@RequestBody (required = false) CommentEntity comment) {
        try {
            return commentService.submitCommentIntoDB(comment);
        } catch (Exception e) {
            throw new ApiRequestException("Could not submit comment");
        }
    }

    @GetMapping("/{postId}")
    private ArrayList<CommentEntity> getCommentsForPost(@PathVariable("postId") String postId) {
        try {
            return commentService.getAllCommentsForDB(postId);
        } catch (Exception e) {
            throw new ApiRequestException("Could not get the comments");
        }
    }
}
