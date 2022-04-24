package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.DetectionRecordMapper;
import indi.hadeslock.server.model.entity.DetectionRecord;
import indi.hadeslock.server.service.IDetectionRecordService;
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
public class DetectionRecordServiceImpl extends ServiceImpl<DetectionRecordMapper, DetectionRecord> implements IDetectionRecordService {

}
