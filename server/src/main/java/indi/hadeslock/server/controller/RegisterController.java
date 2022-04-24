package indi.hadeslock.server.controller;

/*
  Author: Hadeslock
  Created on 2022/4/11 15:42
  Email: hadeslock@126.com
  Desc: 注册控制器
 */

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.pojo.RespBean;
import indi.hadeslock.server.service.IRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "注册操作")
public class RegisterController {

    @Autowired
    private IRegisterService registerService;

    /*
     * @author Hadeslock
     * @time 2022/4/11 20:46
     * 注册用户
     */
    @ApiOperation(value = "注册",notes = "注册新的用户信息")
    @ApiOperationSupport(ignoreParameters = {"roles","id"})
    @PostMapping("/register")
    public RespBean login(@RequestBody User user){
        if(registerService.registerUserWithRole(user, 1)){
            return RespBean.success("注册成功");
        }
        return RespBean.error("注册失败");
    }
}
