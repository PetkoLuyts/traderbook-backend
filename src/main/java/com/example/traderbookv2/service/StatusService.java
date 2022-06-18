package com.example.traderbookv2.service;

import com.example.traderbookv2.model.StatusEntity;
import com.example.traderbookv2.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StatusService {

    @Autowired
    public StatusRepository statusRepository;

    @Autowired
    public UserService userService;
    public StatusEntity submitStatusDataIntoDB(StatusEntity status) {
        return statusRepository.save(status);
    }

    public ArrayList<StatusEntity> retrieveStatus() {
        ArrayList<StatusEntity> statusList = statusRepository.findAll();

        for (int i = 0; i < statusList.size(); i++) {
            StatusEntity status = statusList.get(i);
            status.setUserName(userService.displayUserMetaData(status.getUserId()).getUserName());
        }

        return statusList;
    }
}
