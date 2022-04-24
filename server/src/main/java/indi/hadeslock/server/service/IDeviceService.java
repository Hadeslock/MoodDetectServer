package indi.hadeslock.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.hadeslock.server.model.entity.Device;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface IDeviceService extends IService<Device> {

    /*
     * @author Hadeslock
     * @time 2022/3/28 18:03
     * 根据已登录用户的id查询设备列表
     */
    List<Device> getAllDevices(String keywords);

    /*
     * @author Hadeslock
     * @time 2022/3/28 21:47
     * 添加设备以及设备所有者信息
     */
    int addDevice(Device device);

    /*
     * @author Hadeslock
     * @time 2022/3/29 20:27
     * 根据设备的id删除设备信息
     */
    int deleteDevice(Integer id);

    /*
     * @author Hadeslock
     * @time 2022/3/29 20:57
     * 验证设备id是否属于当前登录用户
     */
    boolean validate(Integer id);

    /*
     * 校验设备是否已经被绑定
     * @author hadeslock
     * @date 2022/4/18 16:07
     * @param device 要校验的设备信息
     * @return int 0-未绑定 1-已被其他用户绑定 2-已被登录用户绑定
     */
    int ifDeviceExist(Device device);
}
