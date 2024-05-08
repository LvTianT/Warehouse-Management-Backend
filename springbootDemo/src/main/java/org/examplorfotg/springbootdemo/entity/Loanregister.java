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
@ApiModel(value="Loanregister对象", description="")
public class Loanregister implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借还ID")
    @TableId(value = "LoanID", type = IdType.AUTO)
    private Integer loanid;

    @ApiModelProperty(value = "产品ID")
    @TableField("ProductID")
    private Integer productid;

    @ApiModelProperty(value = "借还数量")
    @TableField("Quantity")
    private Integer quantity;

    @ApiModelProperty(value = "借还时间")
    @TableField("Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime date;

    @ApiModelProperty(value = "借还人员")
    @TableField("Borrower")
    private String borrower;

    @ApiModelProperty(value = "Y:借 N:还")
    @TableField("operate")
    private String Operate;

}
