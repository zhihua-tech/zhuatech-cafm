/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm;

import cn.zhuatech.cafm.service.SpaceConsolidationDecisionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpaceConsolidationDecisionServiceTests {
    private final SpaceConsolidationDecisionService service = new SpaceConsolidationDecisionService();

    @Test
    void consolidatesPersistentlyUnderusedZone() {
        var result = service.evaluate(new SpaceConsolidationDecisionService.Request(
            "HQ-01", "FLOOR-6", 100, 32, 55, new BigDecimal("3000"), .50, false));

        assertEquals(new BigDecimal("0.3200"), result.averageUtilization());
        assertEquals(45, result.reusableSeats());
        assertEquals("CONSOLIDATE", result.decision());
    }

    @Test
    void keepsCriticalAreaOpen() {
        var result = service.evaluate(new SpaceConsolidationDecisionService.Request(
            "DC-01", "CONTROL", 30, 10, 15, new BigDecimal("5000"), .10, true));

        assertEquals("KEEP", result.decision());
    }
}
