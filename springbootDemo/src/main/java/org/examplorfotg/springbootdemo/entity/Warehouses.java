package org.examplorfotg.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Warehouses对象", description="")
public class Warehouses implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "warehouseId", type = IdType.AUTO)
    private Integer warehouseId;

    @ApiModelProperty(value = "仓库名")
    @TableField("Name")
    private String name;

    @ApiModelProperty(value = "备注")
    @TableField("Remark")
    private String remark;


}
