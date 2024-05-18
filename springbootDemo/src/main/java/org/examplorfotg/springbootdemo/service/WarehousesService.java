package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Warehouses;

public interface WarehousesService extends IService<Warehouses> {
    IPage Warehousepage(IPage<Warehouses> page, Wrapper wrapper);
}
