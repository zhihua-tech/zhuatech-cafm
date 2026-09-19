/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm.service;import jakarta.validation.constraints.NotBlank;import org.springframework.stereotype.Service;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class FacilityWorkOrderClosureService{
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public Assessment assess(Request r){List<String>b=new ArrayList<>(),a=new ArrayList<>();if(!r.safetyPermitClosed())b.add("作业许可尚未关闭");if(!r.completionEvidenceAttached())b.add("缺少完工照片或检测记录");if(!r.assetRecordUpdated())b.add("设施资产履历未更新");if(r.safetyCritical()&&!r.independentVerificationPassed())b.add("安全关键工单未独立复核");if(!b.isEmpty()){a.add("保持工单处理中，补齐安全、证据和资产记录");return new Assessment(Decision.BLOCKED,b,a);}if(!r.requesterAccepted()||!r.costApproved()||!r.preventiveActionAssigned()){if(!r.requesterAccepted())a.add("取得报修人验收确认");if(!r.costApproved())a.add("完成材料与人工成本审批");if(!r.preventiveActionAssigned())a.add("为重复故障指定预防措施");return new Assessment(Decision.REVIEW,b,a);}a.add("关闭工单并固化履历、成本和验收证据");return new Assessment(Decision.CLOSE,b,a);}
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public record Request(@NotBlank String workOrderNo,boolean safetyCritical,boolean safetyPermitClosed,boolean completionEvidenceAttached,boolean assetRecordUpdated,boolean independentVerificationPassed,boolean requesterAccepted,boolean costApproved,boolean preventiveActionAssigned){}/**
                                                                                                                                                                                                                                                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                            */
public record Assessment(Decision decision,List<String> blockers,List<String> actions){}/**
                                                                                                                                                                                                                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                    */
public enum Decision{CLOSE,REVIEW,BLOCKED}}
