package org.examplorfotg.springbootdemo.controller;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.examplorfotg.springbootdemo.common.QueryPageParam;
import org.examplorfotg.springbootdemo.common.Result;
import org.examplorfotg.springbootdemo.entity.Inventory;
import org.examplorfotg.springbootdemo.entity.Inventoryalerts;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.service.InventoryService;
import org.examplorfotg.springbootdemo.service.InventoryalertsService;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.examplorfotg.springbootdemo.service.UsertransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/usertransactions")
public class UsertransactionsController {

    @Autowired
    private UsertransactionsService usertransactionsService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryalertsService inventoryalertsService;
    @Autowired
    private ProductsService productsService;

    @GetMapping("/list")
    public List<Usertransactions> list() {
        return usertransactionsService.list();
    }



    //入库
    @PostMapping("/savein")
    public Result savein(@RequestBody Usertransactions usertransactions) {
        Inventory inventory = inventoryService.getById(usertransactions.getProductid());
        Products products= productsService.getById(usertransactions.getProductid());
        Integer warehouseID=products.getWarehouseId();
        if(inventory==null){
           Inventory inventory1= new Inventory();
           inventory1.setProductId(usertransactions.getProductid());
           inventory1.setQuantity(usertransactions.getInquantity());
           inventory1.setInTime(LocalDateTime.now());
           inventory1.setWarehouseId(warehouseID);
            if ("1".equals(usertransactions.getAction())) {
                LocalDateTime inventoryDateTime = LocalDateTime.now();
                usertransactions.setIntime(inventoryDateTime);
            }
            //如果入库记录添加成功并且库存更新成功，返回添加成功，否则返回失败
            return usertransactionsService.save(usertransactions)&& inventoryService.save(inventory1)? Result.suc() : Result.fail();
        }
        else {
            Inventoryalerts inventoryalerts = inventoryalertsService.getById(usertransactions.getProductid());
            Integer total = inventory.getQuantity();
            Integer instaff = usertransactions.getInstaff();
            Integer inquantity = usertransactions.getInquantity();
            if (inquantity == null || inquantity == 0 || instaff == null || instaff == 0) {
                return Result.NullFormAlert();
            }
            //管理员点击入库，触发事件1，商品增加n个，同时更新入库时间
            if ("1".equals(usertransactions.getAction())) {
                Integer innum = usertransactions.getInquantity();
                total += innum;
                inventory.setQuantity(total);
                LocalDateTime inventoryDateTime = LocalDateTime.now();
                inventory.setInTime(inventoryDateTime);
                usertransactions.setIntime(inventoryDateTime);
                usertransactions.setInquantity(innum);
            }
            //如果入库记录添加成功并且库存更新成功，返回添加成功，否则返回失败
            return usertransactionsService.save(usertransactions) && inventoryService.updateById(inventory) ? Result.suc() : Result.fail();
        }
    }

    //出库及预警功能
    @PostMapping("/saveout")
    public Result saveout(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        //获取商品的所有参数
        Integer productid = (Integer) param.get("productid");
        String action = (String) param.get("action");
        String num1=(String)param.get("outquantity");
        String outstaffname = (String) param.get("outstaffname");
        System.out.println("num1=="+num1);
        //判断用户是否填写完整出库单
        if(StringUtils.isAnyBlank(action,num1,outstaffname)){
            return Result.NullFormAlert();
        }else
        {
        //获取出库数量
        Integer outquantity =  Integer.parseInt(num1);
        Integer outstaff = (Integer) param.get("outstaff");
        Page<Inventory> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        Page<Inventoryalerts> page1 = new Page();
        page1.setCurrent(query.getPageNum());
        page1.setSize(query.getPageSize());
        LambdaQueryWrapper<Inventoryalerts> lambdaQuery = new LambdaQueryWrapper();
        lambdaQuery.eq(Inventoryalerts::getProductid, productid);
        //根据商品id从预警表中获取该商品库存下限
        IPage result1 = inventoryalertsService.Inventoryalertspage(page1, lambdaQuery);
        Inventoryalerts inventoryalerts = (Inventoryalerts) result1.getRecords().get(0);
        Integer lowerlimit = inventoryalerts.getLowerlimit();
        LambdaQueryWrapper<Inventory> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (productid != null) {
            lambdaQueryWrapper.eq(Inventory::getProductId, productid);
            IPage result = inventoryService.Inventorypage(page, lambdaQueryWrapper);
            Inventory inventory = (Inventory) result.getRecords().get(0);
            Integer total = inventory.getQuantity();
              int afterdecrease = total - outquantity;
              //如果出库后低于或等于下限，返回库存不足预警
                  if (afterdecrease <= lowerlimit) {
                    return Result.lowAlert();
                }
                //管理员点击出库，触发事件2，商品减少n个，同时更新出库时间
                else if (action.equals("2")) {
                    LocalDateTime outtime = LocalDateTime.now();
                    Usertransactions usertransactions = new Usertransactions();
                    //新增一条出库记录，记录本次出库时间
                    usertransactions.setOutquantity(outquantity);
                    usertransactions.setOutstaff(outstaff);
                    usertransactions.setOuttime(outtime);
                    usertransactions.setProductid(productid);
                    System.out.println("afterdecrease==" + afterdecrease);
                    //修改商品库存
                    inventory.setQuantity(afterdecrease);
                    LocalDateTime inventoryDateTime = LocalDateTime.now();
                    //修改商品最后一次出库时间
                    inventory.setOutTime(inventoryDateTime);
                    inventoryService.updateById(inventory);
                    return usertransactionsService.save(usertransactions) && inventoryService.updateById(inventory) ? Result.suc() : Result.fail();
            }
          }
        }
        return Result.fail();
    }

    @PostMapping("/listInTime")
    public Result listInTime(@RequestParam String intime) {
        List list = usertransactionsService.lambdaQuery().like(Usertransactions::getIntime, intime).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    @PostMapping("/listOutTime")
    public Result listOutTime(@RequestParam String outtime) {
        List list = usertransactionsService.lambdaQuery().like(Usertransactions::getOuttime, outtime).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String productName = (String) param.get("productName");
        String warehouse = (String) param.get("warehouse");
        String OutStaff = (String) param.get("OutStaff");
        String InStaff = (String) param.get("InStaff");
        String roleId = (String) param.get("roleId");
        Page<Usertransactions> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        QueryWrapper<Usertransactions> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.ProductID=b.ProductID and b.WarehouseID=c.warehouseId and b.productsType=d.Id ");

        if ("2".equals(roleId)) {
            queryWrapper.apply(" a.InStaff= " + InStaff);
            queryWrapper.apply(" a.OutStaff= " + OutStaff);
        }

        if (StringUtils.isNotBlank(productName) && !"null".equals(productName)) {
            queryWrapper.like("b.ProductName", productName);
        }
        if (StringUtils.isNotBlank(warehouse) && !"null".equals(warehouse)) {
            queryWrapper.eq("c.Name", warehouse);
        }

        IPage result = usertransactionsService.Usertransactionspage(page, queryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

}