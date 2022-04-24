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
@TableName("t_patient")
@ApiModel(value="Patient对象")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "被测者姓名")
    private String name;

    @ApiModelProperty(value = "被测者年龄")
    private Integer age;

    @ApiModelProperty(value = "被测者性别")
    private Integer gender;

    @ApiModelProperty(value = "被测者地理位置")
    private String position;

    @ApiModelProperty(value = "被测者识别码")
    private String identity;


}
