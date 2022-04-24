package indi.hadeslock.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.hadeslock.server.model.entity.Patient;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
public interface PatientMapper extends BaseMapper<Patient> {

    /*
     * @author Hadeslock
     * @time 2022/3/30 19:09
     * 根据用户id查询所有病人
     */
    List<Patient> getAllPateints(int userId);
}
