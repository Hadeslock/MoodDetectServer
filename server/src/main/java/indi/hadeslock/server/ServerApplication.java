package indi.hadeslock.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Author: Hadeslock
 * Created on 2022/4/24 13:53
 * Email: hadeslock@126.com
 * Desc: 启动类
 */
@SpringBootApplication
@MapperScan("indi.hadeslock.server.mapper")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
