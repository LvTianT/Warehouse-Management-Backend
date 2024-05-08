package org.examplorfotg.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Inventory对象", description="")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品ID")
    @TableId(value ="ProductID",type = IdType.AUTO)
    private Integer productId;

    @ApiModelProperty(value = "仓库ID")
    @TableField("WarehouseID")
    private Integer warehouseID;

    @ApiModelProperty(value = "库存数量")
    @TableField("Quantity")
    private Integer quantity;

    @ApiModelProperty(value = "最后入库时间")
    @TableField("intime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime inTime;

    @ApiModelProperty(value = "最后出库时间")
    @TableField("outtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime outTime;

}
