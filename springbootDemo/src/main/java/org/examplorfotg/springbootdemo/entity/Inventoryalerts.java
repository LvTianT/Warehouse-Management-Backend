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
@ApiModel(value="Inventoryalerts对象", description="")
public class Inventoryalerts implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预警ID")
    @TableId(value = "AlertID", type = IdType.AUTO)
    private Integer alertid;

    @ApiModelProperty(value = "产品ID")
    @TableField("ProductID")
    private Integer productid;

    @ApiModelProperty(value = "库存下限")
    @TableField("LowerLimit")
    private Integer lowerlimit;

    @ApiModelProperty(value = "库存上限")
    @TableField("UpperLimit")
    private Integer upperlimit;

    @ApiModelProperty(value = "预警级别")
    @TableField("AlertLevel")
    private String alertlevel;


}
