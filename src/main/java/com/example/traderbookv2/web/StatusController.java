package com.example.traderbookv2.web;

import com.example.traderbookv2.model.StatusEntity;
import com.example.traderbookv2.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    public StatusService statusService;

    @PostMapping("")
    public StatusEntity submitStatus(@RequestBody StatusEntity status) {
        return statusService.submitStatusDataIntoDB(status);
    }

    @GetMapping("")
    private ArrayList<StatusEntity> getAllStatus() {
        return statusService.retrieveStatus();
    }
}
