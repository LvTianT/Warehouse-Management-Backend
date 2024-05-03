package org.examplorfotg.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
@ApiModel(value="Usertransactions对象", description="")
public class Usertransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "TransactionID", type = IdType.AUTO)
    private Integer transactionId;

    @ApiModelProperty(value = "用户ID")
    @TableField("UserID")
    private Integer userId;

    @ApiModelProperty(value = "产品ID")
    @TableField("ProductID")
    private Integer productId;

    @ApiModelProperty(value = "仓库ID")
    @TableField("WarehouseID")
    private Integer warehouseId;

    @ApiModelProperty(value = "出/入库人员")
    @TableField("StaffId")
    private Integer staffId;

    @ApiModelProperty(value = "出/入库数量")
    @TableField("Quantity")
    private Integer quantity;

    @ApiModelProperty(value = "时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime time;

    @ApiModelProperty(value = "备注")
    @TableField("Remark")
    private String remark;

    @TableField(exist = false)
    private String action;

}
