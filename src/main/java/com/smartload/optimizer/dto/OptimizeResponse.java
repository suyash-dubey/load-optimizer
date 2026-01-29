package com.smartload.optimizer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OptimizeResponse {

    private String truckId;
    private List<String> selectedOrderIds;

    private long totalPayoutCents;
    private long totalWeightLbs;
    private long totalVolumeCuft;

    private double utilizationWeightPercent;
    private double utilizationVolumePercent;
}

