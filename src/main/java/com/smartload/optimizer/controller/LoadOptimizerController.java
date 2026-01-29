package com.smartload.optimizer.controller;


import com.smartload.optimizer.dto.OptimizeRequest;
import com.smartload.optimizer.dto.OptimizeResponse;
import com.smartload.optimizer.model.Truck;
import com.smartload.optimizer.service.LoadOptimizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {

    private final LoadOptimizerService optimizerService;

    public LoadOptimizerController(LoadOptimizerService optimizerService) {
        this.optimizerService = optimizerService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<?> optimize(@RequestBody OptimizeRequest request) {

        //Null
        if (request == null || request.getTruck() == null) {
            return ResponseEntity.badRequest()
                    .body("Truck details are required");
        }

        Truck truck = request.getTruck();

        //  Invalid truck capacity
        if (truck.getMaxWeightLbs() <= 0 || truck.getMaxVolumeCuft() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Truck capacity must be positive");
        }

        // Orders can be empty
        if (request.getOrders() == null) {
            request.setOrders(List.of());
        }

        OptimizeResponse response = optimizerService.optimize(request);
        return ResponseEntity.ok(response);
    }
}


