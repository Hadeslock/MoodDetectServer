package indi.hadeslock.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.hadeslock.server.model.entity.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface DeviceMapper extends BaseMapper<Device> {

    /*
     * @author Hadeslock
     * @time 2022/3/28 21:20
     * 获取指定id用户的所有设备
     */
    //注意@Param注解一定要写，不然后面xml里面变量就变成param1和param2了
    List<Device> getAllDevices(@Param("id")Integer id, @Param("keywords")String keywords);
}
