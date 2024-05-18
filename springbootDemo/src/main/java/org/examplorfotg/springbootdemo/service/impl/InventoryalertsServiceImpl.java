package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Inventoryalerts;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.mapper.InventoryalertsMapper;
import org.examplorfotg.springbootdemo.mapper.UsertransactionsMapper;
import org.examplorfotg.springbootdemo.service.InventoryalertsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class InventoryalertsServiceImpl extends ServiceImpl<InventoryalertsMapper, Inventoryalerts> implements InventoryalertsService {
    @Resource
    private InventoryalertsMapper inventoryalertsMapper;
    @Override
    public IPage Inventoryalertspage(IPage<Inventoryalerts> page, Wrapper wrapper) {
        return inventoryalertsMapper.Inventoryalertspage(page,wrapper);
    }
}
