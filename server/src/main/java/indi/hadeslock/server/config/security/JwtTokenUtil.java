package indi.hadeslock.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: Hadeslock
 * Created on 2021/12/21 0:40
 * Email: hadeslock@126.com
 * Desc: JwtToken工具类
 */
@Component
public class JwtTokenUtil {

    //用户名key
    public static final String CLAIM_KEY_USERNAME = "sub";
    //创建时间key
    public static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:41
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        //从userDetails中获取用户名以及创建时间
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        //生成token
        return generateToken(claims);
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:42
     * 根据claims负载生成token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                //设置签名方式
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:44
     * 验证token是否有效
     */
    public boolean isValidateToken(String token, UserDetails userDetails){
        //获取用户名
        String username = getUserNameFromToken(token);
        //若用户名不同或token已过期则token无效
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:45
     * 判断token是否过期
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpireDateFromToken(token);
        return expireDate.before(new Date());
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:45
     * 从token中获取过期时间
     */
    private Date getExpireDateFromToken(String token) {
        Date expireDate = null;
        try {
            Claims claims = getClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expireDate;
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:44
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token){
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 0:48
     * 从token中获取荷载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
}
