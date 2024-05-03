package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Warehouses;
import org.examplorfotg.springbootdemo.mapper.WarehousesMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.examplorfotg.springbootdemo.service.WarehousesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WarehousesServiceImpl extends ServiceImpl<WarehousesMapper, Warehouses> implements WarehousesService {
    @Resource
    private WarehousesMapper warehousesMapper;
    @Override
    public IPage pageCC(IPage<Warehouses> page, Wrapper wrapper) {
        return warehousesMapper.pageCC(page,wrapper);
    }
}
