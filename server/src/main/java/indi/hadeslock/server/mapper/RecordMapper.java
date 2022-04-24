package indi.hadeslock.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.hadeslock.server.model.pojo.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordMapper extends BaseMapper<Record> {
    /*
     * 如果指定表名的表不存在，就创建表
     * @author hadeslock
     * @date 2022/4/19 15:41
     * @param tblName 指定的表名
     * @return void
     */
    void createTable(@Param("tblName") String tblName);

    /*
     * 向指定的表名批量插入数据
     * @author hadeslock
     * @date 2022/4/19 16:04
     * @param tblName 指定的表名
     * @param list 要插入的数据集合
     * @return void
     */
    void insertBatch(@Param("tblName") String tblName, @Param("recordList") List<Record> recordList);
    //void insertBatch(@Param("recordList") List<Record> recordList);

    /*
     * 检查指定表名的表是否存在
     * @author hadeslock
     * @date 2022/4/19 16:13
     * @param tblName 指定的表名
     * @return int 1-存在 0-不存在
     */
    int existTable(String tblName);
}
