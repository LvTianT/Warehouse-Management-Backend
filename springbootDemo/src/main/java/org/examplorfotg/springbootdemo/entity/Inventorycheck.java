package org.examplorfotg.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Inventorycheck对象", description="")
public class Inventorycheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "盘库ID")
    @TableId(value = "InventoryCheckID", type = IdType.AUTO)
    private Integer inventorycheckid;

    @ApiModelProperty(value = "产品ID")
    @TableField("ProductID")
    private Integer productid;

    @ApiModelProperty(value = "盘库数量")
    @TableField("CheckedQuantity")
    private Integer checkedquantity;

    @ApiModelProperty(value = "盘库时间")
    @TableField("CheckDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime checkdate;

    @ApiModelProperty(value = "盘库人员")
    @TableField("Checker")
    private String checker;


}
