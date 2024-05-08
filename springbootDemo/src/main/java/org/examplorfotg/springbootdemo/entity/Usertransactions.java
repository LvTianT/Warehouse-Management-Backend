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
@ApiModel(value="Usertransactions对象", description="")
public class Usertransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "TransactionID", type = IdType.AUTO)
    private Integer transactionid;

    @ApiModelProperty(value = "出库人ID")
    @TableField("OutStaff")
    private Integer outstaff;

    @ApiModelProperty(value = "入库人ID")
    @TableField("InStaff")
    private Integer instaff;

    @ApiModelProperty(value = "产品ID")
    @TableField("ProductID")
    private Integer productid;

    @ApiModelProperty(value = "入库量")
    @TableField("InQuantity")
    private Integer inquantity;

    @ApiModelProperty(value = "出库量")
    @TableField("OutQuantity")
    private Integer outquantity;

    @ApiModelProperty(value = "出库时间")
    @TableField("Outtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime outtime;

    @ApiModelProperty(value = "入库时间")
    @TableField("Intime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime intime;

    @ApiModelProperty(value = "备注")
    @TableField("Remark")
    private String remark;

    @TableField(exist = false)
    private String action;

}
