package indi.hadeslock.server.mapper;

import indi.hadeslock.server.model.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /*
     * @author Hadeslock
     * @time 2022/3/26 20:28
     * 根据已登录的用户的id查询所有菜单信息
     */
    List<Menu> getMenusById(Integer id);

    /*
     * @author Hadeslock
     * @time 2022/3/26 23:23
     * 获取所有菜单和角色之间的映射关系，一个菜单对应了多个角色，即可以被多个角色访问
     */
    List<Menu> getMenusByRoles();
}
