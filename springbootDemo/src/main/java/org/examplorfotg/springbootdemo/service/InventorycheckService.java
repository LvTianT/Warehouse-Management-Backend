package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Inventorycheck;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Loanregister;


public interface InventorycheckService extends IService<Inventorycheck> {
    IPage pageCC(IPage<Inventorycheck> page, Wrapper wrapper);
}
