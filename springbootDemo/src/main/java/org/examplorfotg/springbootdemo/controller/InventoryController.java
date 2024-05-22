package org.examplorfotg.springbootdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Inventory;
import org.examplorfotg.springbootdemo.entity.User;
import org.examplorfotg.springbootdemo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Inventory inventory){
        inventory.setOutTime(null);
        return inventoryService.save(inventory)?Result.suc():Result.fail();
    }
    @GetMapping("/list")
    public List<Inventory> list(){
        return inventoryService.list();
    }
    //查询

    @PostMapping("/listInTime")
    public Result listInTime(@RequestParam String intime){
        List list = inventoryService.lambdaQuery().like(Inventory::getInTime,intime).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    @PostMapping("/listOutTime")
    public Result listOutTime(@RequestParam String outtime){
        List list = inventoryService.lambdaQuery().like(Inventory::getOutTime,outtime).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String ProductId = (String) param.get("ProductId");
        String warehouseId = (String) param.get("warehouseId");

        Page<Inventory> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Inventory> lambdaQueryWrapper = new LambdaQueryWrapper();

        if (StringUtils.isNotBlank(ProductId) && !"null".equals(ProductId)) {
            lambdaQueryWrapper.eq(Inventory::getProductId, ProductId);
        }
        if (StringUtils.isNotBlank(warehouseId) && !"null".equals(warehouseId)) {
            lambdaQueryWrapper.eq(Inventory::getWarehouseId, warehouseId);
        }
        IPage result = inventoryService.Inventorypage(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    @PostMapping("/inventlist")
    public Result inventlist(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        Page<Inventory> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Inventory> lambdaQueryWrapper = new LambdaQueryWrapper();
        IPage result = inventoryService.Inventorypage(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

}
