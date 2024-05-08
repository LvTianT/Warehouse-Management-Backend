package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Inventorycheck;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.mapper.InventorycheckMapper;
import org.examplorfotg.springbootdemo.mapper.UsertransactionsMapper;
import org.examplorfotg.springbootdemo.service.InventorycheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class InventorycheckServiceImpl extends ServiceImpl<InventorycheckMapper, Inventorycheck> implements InventorycheckService {
    @Resource
    private InventorycheckMapper inventorycheckMapper;
    @Override
    public IPage pageCC(IPage<Inventorycheck> page, Wrapper wrapper) {
        return inventorycheckMapper.pageCC(page,wrapper);
    }
}
