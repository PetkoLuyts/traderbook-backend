package com.example.traderbookv2.repository;

import com.example.traderbookv2.model.StatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StatusRepository extends CrudRepository<StatusEntity, Integer> {

    StatusEntity save(StatusEntity status);
    ArrayList<StatusEntity> findAll();
}
