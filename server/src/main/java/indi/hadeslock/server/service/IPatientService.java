package indi.hadeslock.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.hadeslock.server.model.entity.Patient;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface IPatientService extends IService<Patient> {

    /*
     * @author Hadeslock
     * @time 2022/3/30 17:55
     * 获取当前用户对应的所有病人
     */
    List<Patient> getAllPateints();

    /*
     * @author Hadeslock
     * @time 2022/3/30 18:08
     * 在当前已登录用户下添加病人信息
     */
    int addPatient(Patient patient);

    /*
     * @author Hadeslock
     * @time 2022/3/30 18:33
     * 根据病人id删除病人信息
     */
    int deletePatientById(int patientId);

    /*
     * @author Hadeslock
     * @time 2022/3/30 18:34
     * 验证病人id是否从属于已登录用户
     */
    boolean validate(Integer id);
}
