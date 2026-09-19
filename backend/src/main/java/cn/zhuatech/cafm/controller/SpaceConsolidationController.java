/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm.controller;

import cn.zhuatech.cafm.common.ApiResponse;
import cn.zhuatech.cafm.service.SpaceConsolidationDecisionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/cafm/insights")
public class SpaceConsolidationController {
    private final SpaceConsolidationDecisionService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SpaceConsolidationController(SpaceConsolidationDecisionService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/space-consolidation")
    public ApiResponse<SpaceConsolidationDecisionService.Result> evaluate(
        @Valid @RequestBody SpaceConsolidationDecisionService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
