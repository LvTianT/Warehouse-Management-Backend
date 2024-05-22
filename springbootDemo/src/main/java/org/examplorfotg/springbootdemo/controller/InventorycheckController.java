package org.examplorfotg.springbootdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Inventorycheck;
import org.examplorfotg.springbootdemo.entity.Loanregister;
import org.examplorfotg.springbootdemo.entity.User;
import org.examplorfotg.springbootdemo.service.InventorycheckService;
import org.examplorfotg.springbootdemo.service.LoanregisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/inventorycheck")
public class InventorycheckController {
    @Autowired
    private InventorycheckService inventorycheckService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Inventorycheck inventorycheck){
        return inventorycheckService.save(inventorycheck)?Result.suc():Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return inventorycheckService.removeById(id)?Result.suc():Result.fail();
    }
    //修改
    @PostMapping("/update")
    public Result update(@RequestBody Inventorycheck inventorycheck){
        return  inventorycheckService.updateById(inventorycheck)?Result.suc():Result.fail();
    }

    @GetMapping("/list")
    public List<Inventorycheck> list(){
        return inventorycheckService.list();
    }

    @PostMapping("/listdatetime")
    public Result listdatetime(@RequestParam String datetime){
        List list =inventorycheckService.lambdaQuery().like(Inventorycheck::getCheckdate,datetime).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    @PostMapping("/Checklist")
    public Result Checklist(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String checker = (String)param.get("checker");
        Page<Inventorycheck> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<Inventorycheck> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(checker) && !"null".equals(checker)){
            lambdaQueryWrapper.like(Inventorycheck::getChecker,checker);
        }
        IPage result = inventorycheckService.Inventorycheckpage(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
}
