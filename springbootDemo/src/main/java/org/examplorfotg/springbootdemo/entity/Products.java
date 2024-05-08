package org.examplorfotg.springbootdemo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Products对象", description="")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品ID")
    @TableId(value = "ProductID", type = IdType.AUTO)
    private Integer productId;

    @ApiModelProperty(value = "产品名称")
    @TableField("ProductName")
    private String productName;

    @ApiModelProperty(value = "产品描述")
    @TableField("ProductDescription")
    private String productDescription;

    @ApiModelProperty(value = "进货价格")
    @TableField("PurchasePrice")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "销售价格")
    @TableField("SalesPrice")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "供应商")
    @TableField("Supplier")
    private String supplier;

    @ApiModelProperty(value = "分类")
    @TableField("productsType")
    private Integer productsType;

    @Getter
    @ApiModelProperty(value = "仓库ID")
    @TableField("WarehouseID")
    private Integer warehouseId;


}
