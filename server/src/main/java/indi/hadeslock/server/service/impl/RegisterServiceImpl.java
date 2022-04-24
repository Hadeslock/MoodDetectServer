package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.UserMapper;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.entity.UserRole;
import indi.hadeslock.server.service.IRegisterService;
import indi.hadeslock.server.service.IUserRoleService;
import indi.hadeslock.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Hadeslock
 * Created on 2022/4/14 14:44
 * Email: hadeslock@126.com
 * Desc: 注册服务实现类
 */
@Service
public class RegisterServiceImpl extends ServiceImpl<UserMapper, User> implements IRegisterService {


    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;

    /*
     * @author Hadeslock
     * @time 2022/4/14 14:45
     * 注册用户，同时添加角色
     */
    @Override
    @Transactional
    public boolean registerUserWithRole(User user, int roleId) {
        boolean flag =  userService.save(user);
        User queryUser = (User) userService.getUserByUserName(user.getUsername());
        UserRole userRole = new UserRole();
        userRole.setUser_id(queryUser.getId());
        userRole.setRole_id(roleId);
        flag &= userRoleService.save(userRole);
        return flag;
    }
}
