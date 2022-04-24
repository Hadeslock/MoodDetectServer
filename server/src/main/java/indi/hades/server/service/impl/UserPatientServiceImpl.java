package indi.hades.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hades.server.mapper.UserPatientMapper;
import indi.hades.server.model.entity.UserPatient;
import indi.hades.server.service.IUserPatientService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-04-24
 */
@Service
public class UserPatientServiceImpl extends ServiceImpl<UserPatientMapper, UserPatient> implements IUserPatientService {

}
