package com.example.traderbookv2.web;

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
    public CommentEntity submitComment(@RequestBody CommentEntity comment) {
        return commentService.submitCommentIntoDB(comment);
    }

    @GetMapping("/{postId}")
    private ArrayList<CommentEntity> getCommentsForPost(@PathVariable("postId") String postId) {
        return commentService.getAllCommentsForDB(postId);
    }
}
