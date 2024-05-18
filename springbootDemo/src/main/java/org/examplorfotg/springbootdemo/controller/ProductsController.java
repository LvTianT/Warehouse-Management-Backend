package org.examplorfotg.springbootdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Inventory;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.entity.User;
import org.examplorfotg.springbootdemo.service.InventoryService;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private InventoryService inventoryService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Products products){

        return productsService.save(products)?Result.suc():Result.fail();
    }
    @PostMapping("/saveAll")
    public Result saveAll(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        Page<Inventory> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Inventory> lambdaQueryWrapper = new LambdaQueryWrapper();
        String productId = (String) param.get("productId");
        String warehouseId = (String) param.get("warehouseId");
        String productsType = (String) param.get("productsType");
        if(StringUtils.isNotBlank(productId) && !"null".equals(productId)){
            lambdaQueryWrapper.like(Inventory::getProductId,productId);
            IPage result = inventoryService.Inventorypage(page,lambdaQueryWrapper);
            if(result.getRecords().isEmpty()){
                Inventory Inventory = new Inventory();
                Inventory.setProductId(Integer.parseInt(productId));
                Inventory.setWarehouseID(Integer.parseInt(warehouseId));
                Products product=new Products();
                product.setProductId(Integer.parseInt(productId));
                product.setProductName((String) param.get("productName"));
                product.setSupplier((String) param.get("supplier"));
                BigDecimal purchasePrice = new BigDecimal(String.valueOf(param.get("purchasePrice")));
                product.setPurchasePrice(purchasePrice);
                BigDecimal salesPrice = new BigDecimal(String.valueOf(param.get("salesPrice")));
                product.setSalesPrice(salesPrice);
                product.setProductsType((Integer) param.get("productsType"));
                product.setWarehouseId(Integer.parseInt(warehouseId));
                product.setProductDescription((String)param.get("productDescription"));
                return productsService.save(product)?Result.suc():Result.fail();
           }
        }
        return Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Products Products){
        return productsService.updateById(Products)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return productsService.removeById(id)?Result.suc():Result.fail();
    }

    //查询所有
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
       HashMap param = query.getParam();
     String productName = (String)param.get("productName");
        String productsType = (String)param.get("productsType");
       String warehouseId = (String)param.get("warehouseId");
        Page<Products> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Products> lambdaQueryWrapper = new LambdaQueryWrapper();

       if(StringUtils.isNotBlank(productName) && !"null".equals(productName)){
            lambdaQueryWrapper.like(Products::getProductName,productName);
        }
        if(StringUtils.isNotBlank(productsType) && !"null".equals(productsType)){
            lambdaQueryWrapper.eq(Products::getProductsType,productsType);
        }
        if(StringUtils.isNotBlank(warehouseId) && !"null".equals(warehouseId)){
            lambdaQueryWrapper.eq(Products::getWarehouseId,warehouseId);
        }
        IPage result = productsService.Productspage(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
    @GetMapping("/list")
    public List<Products> list(){
        return productsService.list();
    }
}
