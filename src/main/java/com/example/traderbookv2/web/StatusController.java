package com.example.traderbookv2.web;

import com.example.traderbookv2.exception.ApiRequestException;
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
    public StatusEntity submitStatus(@RequestBody (required = false) StatusEntity status) {
        try {
            return statusService.submitStatusDataIntoDB(status);
        } catch (Exception e) {
            throw new ApiRequestException("Could not submit status data");
        }
    }

    @GetMapping("")
    private ArrayList<StatusEntity> getAllStatus() {
        try {
            return statusService.retrieveStatus();
        } catch (Exception e) {
            throw new ApiRequestException("Could not get the statuses");
        }
    }
}
