package indi.hadeslock.server.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import indi.hadeslock.server.model.entity.Device;
import indi.hadeslock.server.model.pojo.RespBean;
import indi.hadeslock.server.service.IDeviceService;
import indi.hadeslock.server.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/device")
@Api(tags = "设备操作")
@Slf4j
public class DeviceController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDeviceService deviceService;

    @ApiOperation(value = "查询所有设备", notes = "查询当前用户的所有设备")
    @GetMapping("/allDevices")
    public List<Device> getAllDevices(
            @ApiParam(name = "keywords", value = "查询的关键字")String keywords){
        return deviceService.getAllDevices(keywords);
    }

    @ApiOperation(value = "添加设备", notes = "添加设备以及设备所有者信息")
    @ApiOperationSupport(includeParameters = {
            "device.device_id","device.device_name",
            "userId"
    })
    @PostMapping("/addDevice")
    public RespBean addDevice(@RequestBody Device device){
        //尝试添加设备记录
        int addResult;
        try {
            addResult = deviceService.addDevice(device);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加设备事务失败");
            return RespBean.error("绑定设备失败");
        }
        if(addResult == -1){
            return RespBean.success("绑定设备成功");
        }
        if(addResult == 2){
            return RespBean.error("设备已被其他用户绑定");
        }else if(addResult == 3){
            return RespBean.error("你已经绑定过该设备");
        }
        return RespBean.error("绑定设备失败");
    }

    @ApiOperation(value = "更新设备信息")
    @PutMapping("/")
    public RespBean updateDevice(@RequestBody Device device){
        if(deviceService.updateById(device)){
            return RespBean.success("更新设备信息成功");
        }else{
            return RespBean.error("更新设备信息失败");
        }
    }

    @ApiOperation(value = "解绑设备")
    @PutMapping("/{id}")
    public RespBean unbindDevice(@PathVariable Integer id){
        //先检查要删除的设备是否是已登录用户的，超级管理员也可删除
        if(!deviceService.validate(id)){
            return RespBean.error("解绑设备权限不足");
        }
        //解绑设备
        int res;
        try {
            res = deviceService.deleteDevice(id);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("解绑设备失败");
        }
        //检查删除的结果
        if(res == 1){
            return RespBean.success("解绑设备成功");
        }else if(res == 0){
            return RespBean.error("设备id不存在");
        }else{
            return RespBean.error("解绑设备失败");
        }
    }
}
