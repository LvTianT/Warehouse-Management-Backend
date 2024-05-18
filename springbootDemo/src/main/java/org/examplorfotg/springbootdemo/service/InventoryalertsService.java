package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Inventoryalerts;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Loanregister;


public interface InventoryalertsService extends IService<Inventoryalerts> {
    IPage Inventoryalertspage(IPage<Inventoryalerts> page, Wrapper wrapper);
}
