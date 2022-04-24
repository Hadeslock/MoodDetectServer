package indi.hades.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("t_device")
@ApiModel(value="Device对象")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设备唯一标识")
    private String device_id;

    @ApiModelProperty(value = "设备别名")
    private String device_name;


}
