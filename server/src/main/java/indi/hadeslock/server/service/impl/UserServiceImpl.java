package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.RoleMapper;
import indi.hadeslock.server.mapper.UserMapper;
import indi.hadeslock.server.model.entity.Role;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  用户登录服务
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /*
     * @author Hadeslock
     * @time 2022/3/24 21:31
     * 根据用户名获取用户信息
     */
    @Override
    public UserDetails getUserByUserName(String username) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("username",username));
    }


    @Autowired
    private RoleMapper roleMapper;
    /*
     * @author Hadeslock
     * @time 2022/3/26 23:40
     * 根据用户id获取角色列表
     */
    @Override
    public List<Role> getRolesById(Integer id) {
        return roleMapper.getRolesById(id);
    }

    /*
     * @author Hadeslock
     * @time 2022/3/29 20:53
     * 获取已登录的用户的信息
     */
    @Override
    public User getAuthUser() {
        //获取存储登录信息的principal
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user;
        //从principal中取得登录信息
        if(principal instanceof User){
            user = (User) principal;
        }else if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            //查询登录用户信息
            user = (User) getUserByUserName(username);
        }else{
            return null;
        }
        return user;
    }
}
