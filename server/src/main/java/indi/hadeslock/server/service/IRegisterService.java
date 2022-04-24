package indi.hadeslock.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.hadeslock.server.model.entity.User;

/**
 * Author: Hadeslock
 * Created on 2022/4/14 14:41
 * Email: hadeslock@126.com
 * Desc: 注册服务接口
 */
public interface IRegisterService extends IService<User> {

    /*
     * @author Hadeslock
     * @time 2022/4/14 14:42
     * 注册用户
     */
    boolean registerUserWithRole(User user, int roleId);
}
