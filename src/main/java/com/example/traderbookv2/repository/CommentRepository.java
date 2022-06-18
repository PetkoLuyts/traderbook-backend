package com.example.traderbookv2.repository;

import com.example.traderbookv2.model.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {
    CommentEntity save(CommentEntity comment);
    ArrayList<CommentEntity> findAllByPostId(String postId);
    ArrayList<CommentEntity> findAll();
}
