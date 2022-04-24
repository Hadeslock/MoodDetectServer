package indi.hadeslock.server.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import indi.hadeslock.server.model.entity.Patient;
import indi.hadeslock.server.model.entity.User;
import indi.hadeslock.server.model.pojo.RespBean;
import indi.hadeslock.server.service.IPatientService;
import indi.hadeslock.server.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/patient")
@Api(tags = "病人操作")
@Slf4j
public class PatientController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPatientService patientService;

    @ApiOperation(value = "查询所有病人", notes = "查询当前用户的所有病人")
    @GetMapping("/allPatients")
    public List<Patient> getAllPatients(){
        User user = userService.getAuthUser();
        return patientService.getAllPateints(user.getId());
    }


    @ApiOperation(value = "添加病人", notes = "添加病人以及病人所属者信息")
    @ApiOperationSupport(ignoreParameters = {"patient.id"})
    @PostMapping("/addPatient")
    public RespBean addPatient(@RequestBody Patient patient){
        User user = userService.getAuthUser();
        try {
            return patientService.addPatient(patient, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加病人事务失败");
        }
        return RespBean.error("添加病人失败");
    }

    @ApiOperation(value = "删除病人", notes = "删除病人以及病人所属者信息")
    @DeleteMapping("/{id}")
    public RespBean deletePatient(@PathVariable Integer id){
        //先检查要删除的病人是否是已登录用户的
        if(!patientService.validate(id)){
            return RespBean.error("删除设备权限不足");
        }
        //删除病人信息
        int impact = 0;
        try {
            impact = patientService.deletePatientById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断删除结果并返回响应
        if(impact == 2){
            return RespBean.success("删除病人成功");
        }else{
            return RespBean.success("删除病人失败");
        }
    }

    @ApiOperation(value = "修改病人信息")
    @PutMapping("/")
    public RespBean updatePatient(@RequestBody Patient patient){
        if(patientService.updateById(patient)){
            return RespBean.success("更新病人信息成功");
        }
        return RespBean.error("更新病人信息失败");
    }

}
