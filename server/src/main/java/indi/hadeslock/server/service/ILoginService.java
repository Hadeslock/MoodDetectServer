package indi.hadeslock.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Hadeslock
 * Created on 2022/3/25 14:26
 * Email: hadeslock@126.com
 * Desc: 登录相关服务
 */
public interface ILoginService extends IService<User> {

    /*
     * @author Hadeslock
     * @time 2022/3/24 21:16
     * 登录之后返回token
     */
    RespBean login(String username, String password, String captcha, HttpServletRequest request);
}
