package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Inventory;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.mapper.InventoryMapper;
import org.examplorfotg.springbootdemo.mapper.ProductsMapper;
import org.examplorfotg.springbootdemo.service.InventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
    @Resource
    private InventoryMapper inventoryMapper;
    @Override
    public IPage pageCC(IPage<Inventory> page, Wrapper wrapper) {
        return inventoryMapper.pageCC(page,wrapper);
    }
}
