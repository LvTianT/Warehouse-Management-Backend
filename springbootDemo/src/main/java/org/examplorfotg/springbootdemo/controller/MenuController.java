package org.examplorfotg.springbootdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.examplorfotg.springbootdemo.entity.Menu;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/listAll")
    public List<Menu> listAll(){
        return menuService.list();
    }
    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list = menuService.lambdaQuery().like(Menu::getRoleid,roleId).list();
        return Result.suc(list);
    }
    @PostMapping("/listPage")
    public Result listPage(@RequestParam QueryPageParam query){
        HashMap param = query.getParam();
        String roleid = (String) param.get("roleId");

        Page<Menu> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper();

        if(StringUtils.isNotBlank(roleid) && !"null".equals(roleid)){
            lambdaQueryWrapper.like(Menu::getRoleid,roleid);
        }
        IPage result = menuService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
}
