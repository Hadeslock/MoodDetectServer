package indi.hadeslock.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.hadeslock.server.model.pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Author: Hadeslock
 * Created on 2021/12/21 19:40
 * Email: hadeslock@126.com
 * Desc: 自定义的访问权限拒绝回调
 */

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:41
     * 回调关键函数
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("权限不足，请联系管理员");
        bean.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
