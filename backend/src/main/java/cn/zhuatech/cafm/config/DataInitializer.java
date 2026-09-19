/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cafm.config;

import cn.zhuatech.cafm.model.*;
import cn.zhuatech.cafm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration
public class DataInitializer {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Bean
    CommandLineRunner seed(OperatingUnitRepository operatingUnits, WorkRecordRepository orders,
                           ResourceRegisterRepository resources, ReviewRecordRepository reviewRecords,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (operatingUnits.count() > 0) return;
            OperatingUnit primaryUnit = operatingUnits.save(new OperatingUnit("CAFM-CHEM", "A 区实验楼", "园区运营中心", 180));
            OperatingUnit secondaryUnit = operatingUnits.save(new OperatingUnit("CAFM-MICRO", "数据中心", "研发中心", 120));
            OperatingUnit tertiaryUnit = operatingUnits.save(new OperatingUnit("CAFM-MAT", "园区公共区", "工程中心", 96));

            WorkRecord t1 = orders.save(new WorkRecord("WO-260801-018", "HVAC-A3", "A3 实验区空调温控异常", tertiaryUnit, 24, 16, 1, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "GW-Q3"));
            WorkRecord t2 = orders.save(new WorkRecord("WO-260801-021", "LIGHT-B1", "B1 会议区照明维修", primaryUnit, 18, 8, 0, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "TERM-12"));
            WorkRecord t3 = orders.save(new WorkRecord("WO-260802-006", "SIGN-VIS", "访客中心导视调整", secondaryUnit, 12, 0, 0, LocalDate.now().plusDays(3), WorkRecord.Status.RELEASED, "SP-2026"));
            WorkRecord t4 = orders.save(new WorkRecord("WO-260728-015", "IDC-PM", "数据机房月度巡检", primaryUnit, 20, 20, 1, LocalDate.now(), WorkRecord.Status.COMPLETED, "SEA-09"));

            resources.saveAll(List.of(
                new ResourceRegister("CAT-HPLC-03", "A 区暖通系统", primaryUnit, ResourceRegister.Status.RUNNING, 88),
                new ResourceRegister("CAT-ICP-02", "园区配电系统", primaryUnit, ResourceRegister.Status.IDLE, 76),
                new ResourceRegister("CAT-UTM-05", "空间服务中心", tertiaryUnit, ResourceRegister.Status.RUNNING, 91),
                new ResourceRegister("CAT-INC-08", "地下排水系统", secondaryUnit, ResourceRegister.Status.ALARM, 62)
            ));
            reviewRecords.saveAll(List.of(
                new ReviewRecord("ISS-260801-032", t1, "到场服务核验", 6, 0, ReviewRecord.Result.PASSED, "叶青"),
                new ReviewRecord("ISS-260801-011", t2, "设施状态核验", 3, 0, ReviewRecord.Result.PASSED, "韩屿"),
                new ReviewRecord("ISS-260801-018", t4, "用户验收复核", 5, 1, ReviewRecord.Result.FAILED, "叶青"),
                new ReviewRecord("ISS-260802-003", t3, "服务请求确认", 4, 0, ReviewRecord.Result.PENDING, "韩屿")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "韩屿", UserAccount.Role.DOMAIN_USER, "CAFM-CHEM"),
                new UserAccount("planner", demo, "叶青", UserAccount.Role.DOMAIN_OPERATOR, null),
                new UserAccount("quality", demo, "顾清", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
