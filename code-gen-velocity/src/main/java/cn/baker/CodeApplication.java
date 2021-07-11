package cn.baker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// http://localhost:30000/

/**
 * @author yuanyu
 */
@SpringBootApplication
@MapperScan("cn.baker.mapper")
public class CodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }
}
