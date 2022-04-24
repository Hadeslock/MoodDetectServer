package indi.hadeslock.server.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/*
 * Author: Hadeslock
 * Created on 2021/12/7 0:55
 * Email: hadeslock@126.com
 * Desc: 自定义鉴权管理器，根据URL资源权限和用户角色权限进行鉴权
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o,
                       Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        //遍历之前在CustomFilter中解析得到的和URL相匹配的角色列表
        for (ConfigAttribute configAttribute : collection) {
            String matchRole = configAttribute.getAttribute();
            //如果得到的权限是默认权限，只要用户登录即可访问
            if("ROLE_LOGIN".equals(matchRole)){
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登陆，请登录");
                }else{
                    return;
                }
            }
            // 遍历已登陆用户的角色权限
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                //如果匹配则放行
                if(authority.getAuthority().equals(matchRole)){
                    return;
                }
            }
        }
        // url有角色权限约束但已登录用户的权限不足
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

