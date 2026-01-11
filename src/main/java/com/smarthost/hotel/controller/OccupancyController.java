package com.smarthost.hotel.controller;

import com.smarthost.hotel.dto.OccupancyRequest;
import com.smarthost.hotel.dto.OccupancyResponse;
import com.smarthost.hotel.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/occupancy")
public class OccupancyController {
    @Autowired
    private OccupancyService occupancyService;

    //public OccupancyController(OccupancyService occupancyService) {
     //   this.occupancyService = occupancyService;
    //}

    @PostMapping
    public OccupancyResponse getOccupancy(@RequestBody OccupancyRequest request) {
        return occupancyService.calculateOccupancy(request);
    }
}