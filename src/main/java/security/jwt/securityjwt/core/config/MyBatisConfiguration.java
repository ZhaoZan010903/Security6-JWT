package security.jwt.securityjwt.core.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@MapperScan(
        {
                "security.jwt.securityjwt.account.mapper"
        }
        )
public class MyBatisConfiguration {
    public MyBatisConfiguration() {
        log.info("创建配置类对象：MyBatisConfiguration");
    }

}
