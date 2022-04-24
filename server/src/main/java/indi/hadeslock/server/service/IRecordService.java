package indi.hadeslock.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.hadeslock.server.model.pojo.Record;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Author: Hadeslock
 * Created on 2022/4/14 20:27
 * Email: hadeslock@126.com
 * Desc: 上传记录服务接口
 */
@Transactional
public interface IRecordService extends IService<Record> {

    /*
     * @author Hadeslock
     * @time 2022/4/14 20:27
     * 保存所有上传的测试记录
     */
    int saveRecords(HashMap<String, String> dataMap, List<Record> list);
}
