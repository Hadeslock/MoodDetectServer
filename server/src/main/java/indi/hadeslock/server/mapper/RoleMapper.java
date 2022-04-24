package indi.hadeslock.server.mapper;

import indi.hadeslock.server.model.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /*
     * @author Hadeslock
     * @time 2022/3/26 23:41
     * 根据用户id获取角色列表
     */
    List<Role> getRolesById(Integer id);
}
