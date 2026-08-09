/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.cafm.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceConsolidationDecisionService {
    public Result evaluate(Request request) {
        BigDecimal averageUtilization = BigDecimal.valueOf(request.averageOccupiedSeats())
            .divide(BigDecimal.valueOf(request.totalSeats()), 4, RoundingMode.HALF_UP);
        BigDecimal peakUtilization = BigDecimal.valueOf(request.peakOccupiedSeats())
            .divide(BigDecimal.valueOf(request.totalSeats()), 4, RoundingMode.HALF_UP);
        int reusableSeats = Math.max(0, request.totalSeats() - request.peakOccupiedSeats());
        BigDecimal annualSavings = request.dailyOperatingCost().multiply(BigDecimal.valueOf(250))
            .setScale(2, RoundingMode.HALF_UP);
        String decision = request.criticalArea() || peakUtilization.compareTo(new BigDecimal("0.85")) >= 0
            ? "KEEP" : averageUtilization.compareTo(new BigDecimal("0.45")) < 0
                && peakUtilization.compareTo(new BigDecimal("0.70")) < 0 ? "CONSOLIDATE" : "REVIEW";

        List<String> actions = new ArrayList<>();
        if ("CONSOLIDATE".equals(decision)) actions.add("迁移团队并将空置区域转为共享或退租空间");
        if (request.remoteWorkRate() >= .40) actions.add("采用预约工位并按实际到岗动态开放楼层");
        if ("KEEP".equals(decision)) actions.add("保留现有空间并优化高峰会议室与工位预约");
        if (actions.isEmpty()) actions.add("延长观察周期并采集分时段占用数据");
        return new Result(request.buildingCode(), request.zoneCode(), averageUtilization,
            peakUtilization, reusableSeats, annualSavings, decision, actions);
    }

    public record Request(@NotBlank String buildingCode, @NotBlank String zoneCode,
                          @Min(1) int totalSeats, @Min(0) int averageOccupiedSeats,
                          @Min(0) int peakOccupiedSeats,
                          @DecimalMin("0") BigDecimal dailyOperatingCost,
                          @DecimalMin("0") @DecimalMax("1") double remoteWorkRate,
                          boolean criticalArea) {}

    public record Result(String buildingCode, String zoneCode,
                         BigDecimal averageUtilization, BigDecimal peakUtilization,
                         int reusableSeats, BigDecimal potentialAnnualSavings,
                         String decision, List<String> actions) {}
}
