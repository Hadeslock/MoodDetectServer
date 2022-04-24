package indi.hadeslock.server.service;

import indi.hadeslock.server.model.entity.Role;
import indi.hadeslock.server.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface IUserService extends IService<User> {


    /*
     * @author Hadeslock
     * @time 2022/3/24 21:29
     * 根据用户名获取用户
     */
    UserDetails getUserByUserName(String username);

    /*
     * @author Hadeslock
     * @time 2022/3/26 23:39
     * 根据用户id获取角色列表
     */
    List<Role> getRolesById(Integer id);

    /*
     * @author Hadeslock
     * @time 2022/3/29 20:53
     * 获取当前已登录用户的信息
     */
    User getAuthUser();
}
