package indi.hadeslock.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Author: Hadeslock
 * Created on 2021/12/21 19:17
 * Email: hadeslock@126.com
 * Desc: jwt鉴权的前置过滤器，Security Filter组件
 */

@Component
public class JWTTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:22
     * 校验token，只有token存在才对请求放行，并且将未登录的信息加入登录信息类Authentication
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 拿到token头
        String authHeader = request.getHeader(tokenHeader);
        // 存在token
        if(authHeader!=null && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            // token存在但用户未登录,就进行登录
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效
                if(jwtTokenUtil.isValidateToken(authToken, userDetails)){
                    //设置登陆用户信息
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null, userDetails.getAuthorities());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    // 全局上下文重新设置用户对象，登录
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}
