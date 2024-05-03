package org.examplorfotg.springbootdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.examplorfotg.springbootdemo.service.UsertransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/record")
public class UsertransactionsController {

    @Autowired
    private UsertransactionsService usertransactionsService;

    @Autowired
    private ProductsService productsService;
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodsType = (String)param.get("goodsType");
        String warehouse = (String)param.get("warehouseID");
        String roleId = (String)param.get("roleId");
        String userId = (String)param.get("Id");

        Page<Usertransactions> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Usertransactions> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.products=b.Id and b.storage=c.Id and b.goodsType=d.Id ");

        if("2".equals(roleId)){
            // queryWrapper.eq(Record::getUserid,userId);
            queryWrapper.apply(" a.userId= "+userId);
        }

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)){
            queryWrapper.eq("d.Id",goodsType);
        }
        if(StringUtils.isNotBlank(warehouse) && !"null".equals(warehouse)){
            queryWrapper.eq("c.Id",warehouse);
        }

        IPage result = usertransactionsService.pageCC(page,queryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Usertransactions usertransactions){
        Products products =  productsService.getById(usertransactions.getProductId());
        int n = usertransactions.getQuantity();
        //验证管理员身份，管理员点击出库，触发出库事件，商品减少n个
        if("2".equals(usertransactions.getAction())){
            n = -n;
            usertransactions.setQuantity(n);
        }

        //验证管理员身份，管理员点击入库，触发出库事件，商品增加n个
        int num = products.getQuantity()+n;
        products.setQuantity(num);
        productsService.updateById(products);

        //返回添加成功或失败结果
        return usertransactionsService.save(usertransactions)?Result.suc():Result.fail();
    }
}