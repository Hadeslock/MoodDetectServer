package indi.hadeslock.server.config.security;


import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private RestAutorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private PasswordEncoder customPasswordEncoder;
    @Autowired
    private IUserService userService;
    @Autowired
    private AccessDecisionManager customUrlDecisionManager; //url过滤
    @Autowired
    private FilterInvocationSecurityMetadataSource customFilter; //权限数据源

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:04
     * 通过允许AuthenticationProvider容易地添加来建立认证机制,也就是说用来记录账号，密码，角色信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(customPasswordEncoder);
    }


    /*
     * @author Hadeslock
     * @time 2021/12/21 19:05
     * 自定义的拦截器链，对登录和登出的请求放行，并配置自定义的设置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用jwt，不需要csrf
        http.csrf().disable()
                //基于token，不需要Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //页面请求都需要鉴权认证
                .anyRequest().authenticated()
                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //设置鉴权管理器和数据源
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()
                //禁用缓存
                .headers().cacheControl();
        //添加jwt登录鉴权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }


    @Bean
    public JWTTokenAuthenticationFilter jwtAuthenticationTokenFilter(){
        return new JWTTokenAuthenticationFilter();
    }

    /*
     * @author Hadeslock
     * @time 2022/1/24 14:57
     * 对登录登出以及swagger的资源页面直接放行，不走拦截器链
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                //登录登出、注册
                "/login", "/logout","/register",
                //静态资源
                "/css/**", "/js/**", "/favicon.ico",
                //首页
                "index.html",
                //swagger相关
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha"
        );
    }

    /*
     * @author Hadeslock
     * @time 2022/1/24 18:37
     * 自定义的userDetailsService方法，获取用户信息，在loginController中登录用
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = (User) userService.getUserByUserName(username);//获取用户的基本信息
            if(user != null){
                user.setRoles(userService.getRolesById(user.getId()));//查询用户的角色
                return user;
            }
            throw new UsernameNotFoundException("用户名或密码不正确");
        };
    }
}

