/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm.controller;import cn.zhuatech.cafm.common.ApiResponse;import cn.zhuatech.cafm.service.FacilityWorkOrderClosureService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/cafm")public class FacilityWorkOrderClosureController{private final FacilityWorkOrderClosureService service;/**
                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                              */
public FacilityWorkOrderClosureController(FacilityWorkOrderClosureService service){this.service=service;}/**
                                                                                                                                                                                                                                                                       * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                       */
@PostMapping("/work-order-closure")public ApiResponse<FacilityWorkOrderClosureService.Assessment>assess(@Valid @RequestBody FacilityWorkOrderClosureService.Request request){return ApiResponse.ok(service.assess(request));}}
