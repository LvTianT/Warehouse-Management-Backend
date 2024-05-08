package org.examplorfotg.springbootdemo.controller;


import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Inventoryalerts;
import org.examplorfotg.springbootdemo.service.InventoryalertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/inventoryalerts")
public class InventoryalertsController {
    @Autowired
    private InventoryalertsService inventoryalertsService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Inventoryalerts inventoryalerts){
        return inventoryalertsService.save(inventoryalerts)?Result.suc():Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return inventoryalertsService.removeById(id)?Result.suc():Result.fail();
    }
    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Inventoryalerts inventoryalerts){
        return  inventoryalertsService.updateById(inventoryalerts)?Result.suc():Result.fail();
    }
    //查询
    @GetMapping("/list")
    public List<Inventoryalerts> list(){
        return inventoryalertsService.list();
    }


}
