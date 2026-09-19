/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm.controller;

import cn.zhuatech.cafm.common.ApiResponse;
import cn.zhuatech.cafm.service.SpaceUtilizationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/admin")
public class SpaceUtilizationController {
    private final SpaceUtilizationService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SpaceUtilizationController(SpaceUtilizationService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/space-utilization")
    public ApiResponse<SpaceUtilizationService.Result> analyze(@Valid @RequestBody SpaceUtilizationService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
