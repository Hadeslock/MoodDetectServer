package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.config.security.JwtTokenUtil;
import indi.hadeslock.server.mapper.UserMapper;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.pojo.RespBean;
import indi.hadeslock.server.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Hadeslock
 * Created on 2022/3/25 14:30
 * Email: hadeslock@126.com
 * Desc: 登录服务实现类
 */

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead; // token头

    /*
     * @author Hadeslock
     * @time 2022/3/24 21:16
     * 登录之后返回token
     */
    @Override
    public RespBean login(String username, String password, String captcha, HttpServletRequest request) {
        //校验验证码
        String captchaRef = (String) request.getSession().getAttribute("captcha");
        if(!StringUtils.hasLength(captchaRef)){
            System.out.println(RespBean.error("请先获取验证码"));
            return RespBean.error("请先获取验证码");
        }else if(!StringUtils.hasLength(captcha) || !captchaRef.equalsIgnoreCase(captcha)){
            return RespBean.error("验证码不正确，请重新输入");
        }
        //获取UserDetails，校验用户名和密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String userDetailsUsername = userDetails.getUsername();
        String userDetailsPassword = userDetails.getPassword();
        if(username==null || !username.equals(userDetailsUsername)
                || !password.equals(userDetailsPassword)){
            return RespBean.error("用户名或密码不正确");
        }

        //设置登陆用户信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        // 全局上下文重新设置用户对象，登录
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtil.generateToken(userDetails);// 生成token
        //将token放在返回
        Map<String ,String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

}
