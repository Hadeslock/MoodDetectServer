package indi.hadeslock.server.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.pojo.AdminLoginParam;
import indi.hadeslock.server.model.pojo.RespBean;
import indi.hadeslock.server.service.ILoginService;
import indi.hadeslock.server.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;

@RestController
@Api(tags = "登录操作")
public class LoginController {

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IUserService userService;

    /*
     * @author Hadeslock
     * @time 2021/12/22 0:15
     * 登录的操作，生成token
     */
    @ApiOperation(value = "登录",notes = "登录并生成token，token通过返回消息体返回")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam loginParam, HttpServletRequest request){
        return loginService.login(
                loginParam.getUsername(),
                loginParam.getPassword(),
                loginParam.getCaptcha(),
                request
        );
    }

    /*
     * @author Hadeslock
     * @time 2022/3/24 21:26
     * 查询当前登录的用户的信息
     */
    @ApiOperation(value = "获取已登录用户信息")
    @GetMapping(value = "/admin/info")
    public User getAdminInfo(Principal principal){
        // principal代表当前登录用户的全部信息，登录时在spring-security中已经更新了对象，因此可以获得
        // 这里前端希望得到的不仅仅是用户名和密码，还有登录用户的其他全部信息，因此需要返回一个Admin
        if(principal == null){
            return null;
        }
        String username = principal.getName();
        //查询登录用户信息
        User user = (User) userService.getUserByUserName(username);
        user.setPassword(null); // 即使密码已经加密也不返回密码的信息，防止密码泄露的安全问题
        user.setRoles(userService.getRolesById(user.getId()));
        return user;
    }

    /*
     * @author Hadeslock
     * @time 2022/3/24 21:26
     * 退出登录
     */
    @ApiOperation(value = "退出登录", notes = "简单的返回成功消息，后端不做任何处理，前端将token删除")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");
    }

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    /*
     * @author Hadeslock
     * @time 2022/3/26 20:02
     * 生成验证码
     */
    @ApiOperation(value = "获取验证码")
    //produce使得swagger页面可以正常解析图片
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //-----------------配置请求头传输图片----------------------
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码文本：" + text);
        //将验证码文本内容放入session
        request.getSession().setAttribute("captcha", text);
        //根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片，格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证码 end --------------------------
    }
}
