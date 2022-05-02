package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.PatientMapper;
import indi.hadeslock.server.mapper.UserPatientMapper;
import indi.hadeslock.server.model.entity.Patient;
import indi.hadeslock.server.model.entity.UserPatient;
import indi.hadeslock.server.service.IPatientService;
import indi.hadeslock.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {

    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private UserPatientMapper userPatientMapper;
    @Autowired
    private IUserService userService;

    /*
     * @author Hadeslock
     * @time 2022/3/30 17:55
     * 获取当前用户对应的所有病人
     */
    @Override
    public List<Patient> getAllPateints() {
        int userId = userService.getAuthUser().getId();
        return patientMapper.getAllPateints(userId);
    }

    /*
     * @author Hadeslock
     * @time 2022/3/30 18:08
     * 在当前已登录用户下添加病人信息
     * @return int 数据库受影响的总行数，2表示成功
     */
    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public int addPatient(Patient patient) {
        int userId = userService.getAuthUser().getId();
        int impact = patientMapper.insert(patient);
        UserPatient userPatient = new UserPatient();
        userPatient.setPatient_id(patient.getId());
        userPatient.setUser_id(userId);
        impact += userPatientMapper.insert(userPatient);
        return impact;
    }

    /*
     * @author Hadeslock
     * @time 2022/3/30 18:38
     * 根据病人id删除病人信息
     */
    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public int deletePatientById(int patientId) {
        int impact = userPatientMapper.delete(
                new QueryWrapper<UserPatient>().eq("patient_id", patientId));
        impact += patientMapper.deleteById(patientId);
        return impact;
    }

    /*
     * @author Hadeslock
     * @time 2022/3/30 18:35
     * 验证病人id是否从属于已登录用户
     */
    @Override
    public boolean validate(Integer id) {
        int userId = userService.getAuthUser().getId();
        List<UserPatient> userPatients =
                userPatientMapper.selectList(new QueryWrapper<UserPatient>()
                        .eq("patient_id", id));
        for(UserPatient up: userPatients){
            if(up.getUser_id() == userId){
                return true;
            }
        }
        return false;
    }
}
