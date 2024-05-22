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
import java.time.LocalDateTime;
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
//    @PostMapping("/saveAll")
//    public Result saveAll(@RequestBody Inventory inventory, @RequestBody Products products){
//        Inventory inventory1=new Inventory();
//        inventory1.setProductId(inventory.getProductId());
//        inventory1.setWarehouseId(inventory.getWarehouseId());
//        inventory1.setQuantity(inventory.getQuantity());
//        inventory1.setInTime(LocalDateTime.now());
//        Products products1=new Products();
//        products1.setProductId(products.getProductId());
//        products1.setProductsType(products.getProductsType());
//        products1.setProductName(products.getProductName());
//        products1.setSalesPrice(products.getSalesPrice());
//        products1.setPurchasePrice(products.getPurchasePrice());
//        products1.setWarehouseId(products.getWarehouseId());
//        products1.setProductDescription(products.getProductDescription());
//        products1.setSupplier(products.getSupplier());
//        return inventoryService.save(inventory1)&& productsService.save(products1)?Result.suc():Result.fail();
//
//    }

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
