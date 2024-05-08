package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Inventory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Products;


public interface InventoryService extends IService<Inventory> {
    IPage pageCC(IPage<Inventory> page, Wrapper wrapper);
}
