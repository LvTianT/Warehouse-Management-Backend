package org.examplorfotg.springbootdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.*;
import org.examplorfotg.springbootdemo.service.InventoryService;
import org.examplorfotg.springbootdemo.service.InventoryalertsService;
import org.examplorfotg.springbootdemo.service.LoanregisterService;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @Autowired
    private InventoryalertsService inventoryalertsService;
    //根据时间查询
    @PostMapping("/listdatetime")
    public Result listdatetime(@RequestParam String datetime){
        List list = loanregisterService.lambdaQuery().like(Loanregister::getDate,datetime).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
   //商品还库，库存增加
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
        //还库后用户状态变化
        loanregister.setOperate("N");
        Inventory inventory = inventoryService.getById(productid);
        //库存增加
        Integer total=inventory.getQuantity();
        total+=returnnum;
        inventory.setQuantity(total);
        inventory.setProductId(productid);
        return  loanregisterService.updateById(loanregister)&&inventoryService.updateById(inventory)?Result.suc():Result.fail();
    }


    @PostMapping("/save")
    public Result save(@RequestBody Loanregister loanregister){
        Integer productid=loanregister.getProductid();
        Integer quantity=loanregister.getQuantity();
        //从库存表中查询该商品
        Inventory inventory = inventoryService.getById(productid);
        //获取该商品当前库存
        Integer total=inventory.getQuantity();
        //从商品表中查询该商品
        Products products=productsService.getById(productid);
        //填写借条前判断数量是否为空
        if(quantity==null || quantity<0){
           return Result.fail();
        }
        //判断商品编号是否存在
        if(products==null) {
            return Result.noProduct();
        }
        //从预警表中获取该商品库存下限
        Inventoryalerts  inventoryalerts=inventoryalertsService.getById(productid);
        Integer lowerlimit=inventoryalerts.getLowerlimit();
        Integer afterdecrease = total - quantity;
        //判断借出该数量后库存是否足够
        if (afterdecrease <0) {
            //库存不足，无法借出商品，同时提示即时补货
            return Result.lowAlert();
        }else{
        //库存足够，借出商品，库存减少
        inventory.setQuantity(afterdecrease);
        inventory.setOutTime(LocalDateTime.now());
        inventory.setProductId(productid);
        return  loanregisterService.save(loanregister)&&inventoryService.updateById(inventory)?Result.suc():Result.fail();
        }
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
