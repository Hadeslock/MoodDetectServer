package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.UserDeviceMapper;
import indi.hadeslock.server.model.entity.UserDevice;
import indi.hadeslock.server.service.IUserDeviceService;
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
public class UserDeviceServiceImpl extends ServiceImpl<UserDeviceMapper, UserDevice> implements IUserDeviceService {

}
