package indi.hadeslock.server.service.impl;

import indi.hadeslock.server.model.entity.Menu;
import indi.hadeslock.server.mapper.MenuMapper;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /*
     * @author Hadeslock
     * @time 2022/3/26 23:22
     * 根据已登录的用户的id查询所有菜单信息
     */
    @Override
    public List<Menu> getMenusById() {
        Integer id = ((User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId();
        return menuMapper.getMenusById(id);
    }

    /*
     * @author Hadeslock
     * @time 2022/3/26 23:22
     * 获取所有菜单和角色之间的映射关系，一个菜单对应了多个角色，即可以被多个角色访问
     */
    @Override
    public List<Menu> getMenusByRoles() {
        return menuMapper.getMenusByRoles();
    }
}
