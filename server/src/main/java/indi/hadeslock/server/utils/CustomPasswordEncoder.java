package indi.hadeslock.server.utils;


/*
 * Author: Hadeslock
 * Created on 2021/12/21 23:33
 * Email: hadeslock@126.com
 * Desc: 自定义密码编码器配置
 */

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:05
     * 自定义的密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
