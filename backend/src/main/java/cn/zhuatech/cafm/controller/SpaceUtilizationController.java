/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.cafm.controller;

import cn.zhuatech.cafm.common.ApiResponse;
import cn.zhuatech.cafm.service.SpaceUtilizationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class SpaceUtilizationController {
    private final SpaceUtilizationService service;
    public SpaceUtilizationController(SpaceUtilizationService service) { this.service = service; }
    @PostMapping("/space-utilization")
    public ApiResponse<SpaceUtilizationService.Result> analyze(@Valid @RequestBody SpaceUtilizationService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
