package indi.hadeslock.server.controller;


import indi.hadeslock.server.model.entity.Menu;
import indi.hadeslock.server.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/menu")
@Api(tags = "菜单操作")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "查询所有菜单",
            notes = "根据用户id查询所有菜单,用户id固定为当前登录的用户的id")
    @GetMapping("/allMenu")
    public List<Menu> getMenusById(){
        return menuService.getMenusById();
    }

}
