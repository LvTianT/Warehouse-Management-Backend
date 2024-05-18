package org.examplorfotg.springbootdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.*;
import org.examplorfotg.springbootdemo.service.InventoryService;
import org.examplorfotg.springbootdemo.service.LoanregisterService;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/loanregister")
public class LoanregisterController {
    @Autowired
    private LoanregisterService loanregisterService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductsService productsService;
    //根据时间查询
    @PostMapping("/listdatetime")
    public Result listdatetime(@RequestParam String datetime){
        List list = loanregisterService.lambdaQuery().like(Loanregister::getDate,datetime).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    @PostMapping("/returnpro")
    public Result returnpro(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        Integer loanid = (Integer)param.get("loanid");
        Integer productid = (Integer)param.get("productid");
        Integer returnnum=(Integer)param.get("quantity");
        String operate=(String)param.get("operate");
        System.out.println(operate);
        Page<Loanregister> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Loanregister> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(loanid!=null && loanid>0&&operate.equals("Y")){
            lambdaQueryWrapper.eq(Loanregister::getLoanid,loanid);
        }else{
            return Result.fail();
        }

        IPage result = loanregisterService.Loanregisterpage(page,lambdaQueryWrapper);
        Loanregister loanregister= (Loanregister)result.getRecords().get(0);
        loanregister.setOperate("N");
        System.out.println("total=="+result.getTotal());
        System.out.println("loanregister=="+loanregister);
        Inventory inventory = inventoryService.getById(productid);
        Integer total=inventory.getQuantity();
        total+=returnnum;
        inventory.setQuantity(total);
        inventory.setProductId(productid);
        return  loanregisterService.updateById(loanregister)&&inventoryService.updateById(inventory)?Result.suc():Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Loanregister loanregister){
        Integer productid=loanregister.getProductid();
        System.out.println(productid);
        Integer quantity=loanregister.getQuantity();
        Products products=productsService.getById(productid);
        if(quantity==null || quantity<0){
           return Result.fail();
        }
        if(products==null){
            return Result.noProduct();
        }
        Inventory inventory = inventoryService.getById(productid);
        Integer total=inventory.getQuantity();
        total-=quantity;
        inventory.setQuantity(total);
        inventory.setProductId(productid);
        return  loanregisterService.save(loanregister)&&inventoryService.updateById(inventory)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Loanregister loanregister){
        return  loanregisterService.updateById(loanregister)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return loanregisterService.removeById(id)?Result.suc():Result.fail();
    }

    //查询
    @GetMapping("/list")
    public List<Loanregister> list(){
        return loanregisterService.list();
    }

    @PostMapping("/loanregisterlist")
    public Result loanregisterlist(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String borrower = (String)param.get("borrower");
        Page<Loanregister> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Loanregister> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(borrower) && !"null".equals(borrower)){
            lambdaQueryWrapper.like(Loanregister::getBorrower,borrower);
        }

        IPage result = loanregisterService.Loanregisterpage(page, lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return Result.suc(result.getRecords(),result.getTotal());
    }
    @PostMapping("/listPageC")
    public List<Loanregister> listPageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String  borrower = (String)param.get("borrower");

        Page<Loanregister> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Loanregister> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Loanregister::getBorrower,borrower);

        IPage result = loanregisterService.Loanregisterpage(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }
}
