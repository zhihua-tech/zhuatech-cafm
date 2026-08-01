# Cafm 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

浏览器通过 Vue 管理端或设施服务工程师端访问 Spring Boot REST API。安全层完成 JWT 与角色鉴权，业务层负责服务工单、商品、设施台账、核验和结果记录，JPA/Flyway 管理 MySQL 数据。

管理端角色为 `DOMAIN_OPERATOR`、`QUALITY`、`ADMIN`；执行端角色为 `DOMAIN_USER`。正式部署建议将设施台账连接置于独立采集服务，并隔离服务区域网络和办公网络。
