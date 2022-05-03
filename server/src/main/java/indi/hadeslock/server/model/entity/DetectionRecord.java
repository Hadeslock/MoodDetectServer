package indi.hadeslock.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * <p>
 *
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_detection_record")
@ApiModel(value="DetectionRecord对象")
public class DetectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "测试记录id")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer record_id;

    @ApiModelProperty(value = "设备编号")
    private Integer device_id;

    @ApiModelProperty(value = "病人id")
    private Integer patient_id;

    @ApiModelProperty(value = "日期")
    private Date date;

    @ApiModelProperty(value = "开始时间")
    private Time begin_time;

    @ApiModelProperty(value = "结束时间")
    private Time end_time;

    @ApiModelProperty(value = "关键时间信息")
    private String key_time;

    public DetectionRecord() {
    }

    public DetectionRecord(Integer deviceId, int patientId, Date date, Time startTime, Time endTime, String keyTime) {
        this.device_id = deviceId;
        this.patient_id = patientId;
        this.date = date;
        this.begin_time = startTime;
        this.end_time = endTime;
        this.key_time = keyTime;
    }
}