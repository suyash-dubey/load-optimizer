package com.smartload.optimizer.service;

import com.smartload.optimizer.dto.OptimizeRequest;
import com.smartload.optimizer.dto.OptimizeResponse;
import com.smartload.optimizer.model.Order;
import com.smartload.optimizer.model.Truck;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
//@Data
@Service
public class LoadOptimizerService {



    public OptimizeResponse optimize(OptimizeRequest request) {

        Truck truck = request.getTruck();
        List<Order> orders = request.getOrders();

        // Split by hazmat
        List<Order> hazmatOrders = orders.stream()
                .filter(Order::isHazmat)
                .toList();

        List<Order> nonHazmatOrders = orders.stream()
                .filter(o -> !o.isHazmat())
                .toList();

        OptimizeResponse hazmatResult = null;
        OptimizeResponse nonHazmatResult = null;

        if (!hazmatOrders.isEmpty()) {
            hazmatResult = optimizeSubset(truck, hazmatOrders);
        }

        if (!nonHazmatOrders.isEmpty()) {
            nonHazmatResult = optimizeSubset(truck, nonHazmatOrders);
        }

        // Choose better payout
        if (hazmatResult == null) return nonHazmatResult;
        if (nonHazmatResult == null) return hazmatResult;

        return hazmatResult.getTotalPayoutCents() >
                nonHazmatResult.getTotalPayoutCents()
                ? hazmatResult
                : nonHazmatResult;
    }
    private OptimizeResponse optimizeSubset(Truck truck, List<Order> orders) {

        long bestPayout = 0;
        long bestWeight = 0;
        long bestVolume = 0;
        List<String> bestOrderIds = new ArrayList<>();

        // Single orders
        for (Order o : orders) {
            if (o.getWeightLbs() <= truck.getMaxWeightLbs()
                    && o.getVolumeCuft() <= truck.getMaxVolumeCuft()) {

                if (o.getPayoutCents() > bestPayout) {
                    bestPayout = o.getPayoutCents();
                    bestWeight = o.getWeightLbs();
                    bestVolume = o.getVolumeCuft();
                    bestOrderIds = List.of(o.getId());
                }
            }
        }

        // Pairs (you can extend to DP later)
        for (int i = 0; i < orders.size(); i++) {
            for (int j = i + 1; j < orders.size(); j++) {

                Order a = orders.get(i);
                Order b = orders.get(j);

                long totalWeight = a.getWeightLbs() + b.getWeightLbs();
                long totalVolume = a.getVolumeCuft() + b.getVolumeCuft();
                long totalPayout = a.getPayoutCents() + b.getPayoutCents();

                if (totalWeight <= truck.getMaxWeightLbs()
                        && totalVolume <= truck.getMaxVolumeCuft()
                        && totalPayout > bestPayout) {

                    bestPayout = totalPayout;
                    bestWeight = (int) totalWeight;
                    bestVolume = (int) totalVolume;
                    bestOrderIds = List.of(a.getId(), b.getId());
                }
            }
        }

        return buildResponse(truck, bestOrderIds, bestPayout, bestWeight, bestVolume);
    }

    private OptimizeResponse buildResponse(
            Truck truck,
            List<String> orderIds,
            long payout,
            long weight,
            long volume
    ) {
        OptimizeResponse r = new OptimizeResponse();
        r.setTruckId(truck.getId());
        r.setSelectedOrderIds(orderIds);
        r.setTotalPayoutCents(payout);
        r.setTotalWeightLbs(weight);
        r.setTotalVolumeCuft(volume);
        r.setUtilizationWeightPercent(
                (weight * 100.0) / truck.getMaxWeightLbs()
        );
        r.setUtilizationVolumePercent(
                (volume * 100.0) / truck.getMaxVolumeCuft()
        );
        return r;
    }
}


