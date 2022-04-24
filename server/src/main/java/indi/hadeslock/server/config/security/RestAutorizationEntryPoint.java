package indi.hadeslock.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.hadeslock.server.model.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Author: Hadeslock
 * Created on 2021/12/21 19:57
 * Email: hadeslock@126.com
 * Desc: 自定义的登录增强点
 */

@Component
public class RestAutorizationEntryPoint implements AuthenticationEntryPoint {
    /*
     * @author Hadeslock
     * @time 2021/12/21 19:58
     * 进行认证，这里简单的返回要求登录的消息
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("尚未登录，请登录");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
