package indi.hadeslock.server.model.pojo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Author: Hadeslock
 * Created on 2022/3/31 13:34
 * Email: hadeslock@126.com
 * Desc: 导入导出的测试记录pojo对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="测试记录pojo对象")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "日期",format = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "时间")
    @Excel(name = "时间")
    private Time time;

    @ApiModelProperty(value = "电位数据")
    @Excel(name = "电位数据")
    private double potential;

}