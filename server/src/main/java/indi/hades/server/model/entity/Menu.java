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
@TableName("t_menu")
@ApiModel(value="Menu对象")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "访问路径")
    private String url;

    @ApiModelProperty(value = "前端资源路径")
    private String path;

    @ApiModelProperty(value = "前端菜单名")
    private String name;

    @ApiModelProperty(value = "父菜单id")
    private Integer parent_id;

    @ApiModelProperty(value = "前端组件名")
    private String component;

    @ApiModelProperty(value = "前端菜单图标名称")
    private String icon;


}
