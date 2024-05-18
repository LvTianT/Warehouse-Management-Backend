package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.mapper.UsertransactionsMapper;
import org.examplorfotg.springbootdemo.service.UsertransactionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.examplorfotg.springbootdemo.entity.Warehouses;
import org.examplorfotg.springbootdemo.mapper.WarehousesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UsertransactionsServiceImpl extends ServiceImpl<UsertransactionsMapper, Usertransactions> implements UsertransactionsService {
    @Resource
    private UsertransactionsMapper usertransactionsMapper;
    @Override
    public IPage Usertransactionspage(IPage<Usertransactions> page, Wrapper wrapper) {
        return usertransactionsMapper.Usertransactionspage(page,wrapper);
    }
}
