package org.examplorfotg.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单编码")
    @TableField("menuCode")
    private String menucode;

    @ApiModelProperty(value = "菜单名字")
    @TableField("menuName")
    private String menuname;

    @ApiModelProperty(value = "菜单级别")
    @TableField("menuLevel")
    private String menulevel;

    @ApiModelProperty(value = "菜单的父code")
    @TableField("parentMenuCode")
    private String parentmenucode;

    @ApiModelProperty(value = "点击触发的函数")
    @TableField("click")
    private String menuclick;

    @ApiModelProperty(value = "权限 0 高级管理员 1 管理员 2 普通用户")
    @TableField("roleId")
    private String roleid;

    @TableField("menuComponent")
    private String menucomponent;

    @TableField("menuIcon")
    private String menuicon;
}
