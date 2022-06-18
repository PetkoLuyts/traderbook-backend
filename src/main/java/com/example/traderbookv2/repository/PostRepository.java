package com.example.traderbookv2.repository;

import com.example.traderbookv2.model.PostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Integer> {
    PostEntity save(PostEntity post);
    ArrayList<PostEntity> findAll();
}
