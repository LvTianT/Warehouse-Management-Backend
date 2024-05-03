package org.examplorfotg.springbootdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/Products")
public class ProductsController {

    @Autowired
    private ProductsService ProductsService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Products Products){
        return ProductsService.save(Products)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Products Products){
        return ProductsService.updateById(Products)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return ProductsService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String productType = (String)param.get("productType");
        String warehouseId = (String)param.get("warehouseId");

        Page<Products> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Products> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Products::getProductName,name);
        }
        if(StringUtils.isNotBlank(productType) && !"null".equals(productType)){
            lambdaQueryWrapper.eq(Products::getProductType,productType);
        }
        if(StringUtils.isNotBlank(warehouseId) && !"null".equals(warehouseId)){
            lambdaQueryWrapper.eq(Products::getWarehouseId,warehouseId);
        }
        IPage result = ProductsService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
}
