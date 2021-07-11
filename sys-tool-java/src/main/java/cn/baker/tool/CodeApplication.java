package cn.baker.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

// http://localhost:40000/swagger-ui/index.html

/**
 * @author yuanyu
 */
@SpringBootApplication
@MapperScan("cn.baker.tool.mapper")
public class CodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }
}
