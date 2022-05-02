package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.DeviceMapper;
import indi.hadeslock.server.mapper.UserDeviceMapper;
import indi.hadeslock.server.model.entity.Device;
import indi.hadeslock.server.model.entity.Role;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.entity.UserDevice;
import indi.hadeslock.server.service.IDeviceService;
import indi.hadeslock.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private UserDeviceMapper userDeviceMapper;
    @Autowired
    private IUserService userService;

    /*
     * @author Hadeslock
     * @time 2022/3/28 21:20
     * 获取指定id用户的所有设备
     */
    @Override
    public List<Device> getAllDevices(String keywords) {
        return deviceMapper.getAllDevices(userService.getAuthUser().getId(), keywords);
    }

    /*
     * 添加设备以及设备所有者信息
     * @author hadeslock
     * @date 2022/4/18 16:19
     * @param userId 设备添加者id
     * @param device 要添加的设备
     * @return int -1 - 添加成功
     */
    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public int addDevice(Device device) {
        //先对设备绑定状态进行验证
        int bindResult = ifDeviceExist(device);
        if(bindResult >= 2){
            return bindResult;
        }
        //设备不存在才插入设备表
        if(bindResult == 0)
            deviceMapper.insert(device);
        //插入设备用户连接表
        if(bindResult == 0 || bindResult == 1){
            //获取设备在数据库中的id
            Device existDevice = deviceMapper.selectOne(
                    new QueryWrapper<Device>().eq("device_id", device.getDevice_id()));
            UserDevice userDevice = new UserDevice();
            userDevice.setUser_id(userService.getAuthUser().getId());
            userDevice.setDevice_id(existDevice.getId());
            userDeviceMapper.insert(userDevice);
        }
        return -1;
    }

    /*
     * @author Hadeslock
     * @time 2022/3/29 20:27
     * 根据设备的id删除设备信息
     */
    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public int deleteDevice(Integer id) {
        //删除联结表的数据
        return userDeviceMapper.delete(new QueryWrapper<UserDevice>().eq("device_id", id));

    }

    /*
     * @author Hadeslock
     * @time 2022/3/29 20:58
     * 验证设备id是否属于当前登录用户,超级管理员除外
     */
    @Override
    public boolean validate(Integer id) {
        //校验当前用户的权限是否为超级管理员
        User user = userService.getAuthUser();
        for (Role role : user.getRoles()) {
            if("超级管理员".equals(role.getName())){
                return true;
            }
        }
        //校验用户的id是否和设备的id绑定
        int userId = user.getId();
        List<UserDevice> userDevices =
                userDeviceMapper.selectList(new QueryWrapper<UserDevice>()
                        .eq("device_id", id));
        for(UserDevice ud: userDevices){
            if(ud.getUser_id() == userId){
                return true;
            }
        }
        return false;
    }

    /*
     * 校验设备是否已经被绑定
     * @author hadeslock
     * @date 2022/4/18 16:07
     * @param device 要校验的设备信息
     * @return int 0-设备不存在 1-已存在但未绑定 2-已被其他用户绑定 3-已被登录用户绑定
     */
    public int ifDeviceExist(Device device){
        //查询设备表里面是否存在设备mac对应的设备
        Device boundDevice = deviceMapper.selectOne(
                new QueryWrapper<Device>().eq("device_id", device.getDevice_id()));
        if(boundDevice == null){
            //设备mac不存在
            return 0;
        }else{
            //设备mac存在，获取设备信息
            //去设备和用户的关联表查记录
            UserDevice userDevice = userDeviceMapper.selectOne(
                    new QueryWrapper<UserDevice>().eq("device_id", boundDevice.getId()));
            if(userDevice == null){
                return 1;
            }
            User authUser = userService.getAuthUser(); //已登录用户
            if(!Objects.equals(userDevice.getUser_id(), authUser.getId())){
                //设备不属于已登录用户
                return 2;
            }else{
                return 3;
            }
        }
    }
}
