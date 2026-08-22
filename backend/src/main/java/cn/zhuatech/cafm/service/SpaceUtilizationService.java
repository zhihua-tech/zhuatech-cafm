/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceUtilizationService {
    public Result analyze(Request request) {
        int unusedSeats = Math.max(0, (int) Math.round(request.seats() * (1 - request.averageOccupancyPercent() / 100.0)));
        BigDecimal annualSavings = request.monthlyCost().multiply(BigDecimal.valueOf(1 - request.averageOccupancyPercent() / 100.0))
            .multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(.6)).setScale(2, RoundingMode.HALF_UP);
        String decision = request.averageOccupancyPercent() < 50 && request.peakOccupancyPercent() < 75 ? "CONSOLIDATE"
            : request.averageOccupancyPercent() < 70 ? "REBALANCE" : "KEEP";
        List<String> actions = new ArrayList<>();
        if ("CONSOLIDATE".equals(decision)) actions.add("评估合并低利用区域并释放租赁面积");
        if (request.flexibleSeats() < unusedSeats / 2) actions.add("将部分固定工位改为共享工位");
        if (actions.isEmpty()) actions.add("保持当前空间配置并持续采集客流");
        return new Result(request.spaceName(), unusedSeats, annualSavings, decision, actions);
    }

    public record Request(@NotBlank String spaceName, @Positive double areaSqm,
                          @Positive int seats,
                          @DecimalMin("0") @DecimalMax("100") double averageOccupancyPercent,
                          @DecimalMin("0") @DecimalMax("100") double peakOccupancyPercent,
                          @DecimalMin("0") BigDecimal monthlyCost, @Min(0) int flexibleSeats) {}
    public record Result(String spaceName, int unusedSeats, BigDecimal annualSavingsPotential,
                         String decision, List<String> actions) {}
}
