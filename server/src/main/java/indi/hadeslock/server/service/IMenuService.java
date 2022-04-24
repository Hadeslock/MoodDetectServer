package indi.hadeslock.server.service;

import indi.hadeslock.server.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface IMenuService extends IService<Menu> {

    /*
     * @author Hadeslock
     * @time 2022/3/26 20:27
     * 根据已登录用户id查询所有菜单
     */
    List<Menu> getMenusById();

    /*
     * @author Hadeslock
     * @time 2022/3/26 23:22
     * 获取所有菜单和角色之间的映射关系，一个菜单对应了多个角色，即可以被多个角色访问
     */
    List<Menu> getMenusByRoles();
}
