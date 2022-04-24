package indi.hades.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Hadeslcok
 * @since 2022-04-24
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

    @ApiModelProperty(value = "测试日期")
    private LocalDate date;

    @ApiModelProperty(value = "开始时间")
    private LocalTime begin_time;

    @ApiModelProperty(value = "结束时间")
    private LocalTime end_time;


}
