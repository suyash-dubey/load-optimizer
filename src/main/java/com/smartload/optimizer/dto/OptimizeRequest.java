package com.smartload.optimizer.dto;

//package com.smartload.optimizer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;
import com.smartload.optimizer.model.Truck;
import com.smartload.optimizer.model.Order;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OptimizeRequest {
    private Truck truck;
    private List<Order> orders;
}

