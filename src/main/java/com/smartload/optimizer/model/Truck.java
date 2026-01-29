package com.smartload.optimizer.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Truck {

    private String id;
    private int maxWeightLbs;
    private int maxVolumeCuft;
}