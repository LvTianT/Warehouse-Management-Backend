package org.examplorfotg.springbootdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Warehouses;
import org.examplorfotg.springbootdemo.service.WarehousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Warehouses")
public class WarehousesController {

    @Autowired
    private WarehousesService warehousesService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Warehouses Warehouses){
        return warehousesService.save(Warehouses)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Warehouses Warehouses){
        return warehousesService.updateById(Warehouses)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return warehousesService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String warehouseId = (String)param.get("warehouseId");
        Page<Warehouses> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Warehouses> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Warehouses::getName,name);
        }
        if(StringUtils.isNotBlank( warehouseId) && !"null".equals( warehouseId)){
            lambdaQueryWrapper.eq(Warehouses::getWarehouseId,warehouseId);
        }

        IPage result = warehousesService.Warehousepage(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list = warehousesService.list();
        return Result.suc(list);
    }
}
