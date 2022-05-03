package indi.hadeslock.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.hadeslock.server.mapper.DetectionRecordMapper;
import indi.hadeslock.server.mapper.DeviceMapper;
import indi.hadeslock.server.mapper.RecordMapper;
import indi.hadeslock.server.model.entity.DetectionRecord;
import indi.hadeslock.server.model.entity.Device;
import indi.hadeslock.server.model.pojo.Record;
import indi.hadeslock.server.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Hadeslock
 * Created on 2022/4/14 20:29
 * Email: hadeslock@126.com
 * Desc: 上传测试记录服务实现类
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Autowired
    private DetectionRecordMapper detectionRecordMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private RecordMapper recordMapper;

    /*
     * 保存相应的病人记录和设备记录，并且保存到病人对应的表中
     * @author hadeslock
     * @date 2022/4/19 20:19
     * @param dataMap 存储了设备、病人、开始结束时间信息的map
     * @param list 测量记录list
     * @return int -1-事务成功 0-事务失败 1-重复添加
     */
    @Override
    public int saveRecords(HashMap<String, String> dataMap, List<Record> list) {
        //从map中获取各种信息
        String deviceMac = dataMap.get("deviceMac");
        int patientId = Integer.parseInt(dataMap.get("patientId"));
        Date date = Date.valueOf(dataMap.get("date"));
        Time startTime = Time.valueOf(dataMap.get("startTime"));
        Time endTime = Time.valueOf(dataMap.get("endTime"));
        String keyTime = dataMap.get("keyTime");
        //根据deviceMac查出设备信息
        Device device = deviceMapper.selectOne(new QueryWrapper<Device>().eq("device_id", deviceMac));
        //先查一下指定的设备和病人在时间段内有没有记录
        List<DetectionRecord> detectionRecords = detectionRecordMapper.selectList(
                new QueryWrapper<DetectionRecord>()
                        .eq("device_id", device.getId())
                        .eq("patient_id", patientId));
        for (DetectionRecord detectionRecord : detectionRecords) {
            Date detect_date = detectionRecord.getDate();
            Time begin_time = detectionRecord.getBegin_time();
            Time end_time = detectionRecord.getEnd_time();
            // TODO: 2022/4/20 这里应该需要处理一下跨越多天的记录
            if(detect_date.equals(date) && (begin_time.before(startTime) || begin_time.equals(startTime)) &&
                    (end_time.after(endTime) || end_time.equals(endTime))){
                //有记录就返回false
                return 1;
            }
        }
        //保存单次测试记录
        DetectionRecord detectionRecord =
                new DetectionRecord(device.getId(), patientId, date, startTime, endTime, keyTime);
        detectionRecordMapper.insert(detectionRecord);
        //检查对应的病人记录表是否存在,不存在就创建
        String tblName = "t_" + patientId + "_detection_record";
        if (recordMapper.existTable(tblName) == 0) {
            recordMapper.createTable(tblName);
        }
        //往指定的表添加数据
        recordMapper.insertBatch(tblName, list);
        return -1;
    }
}
