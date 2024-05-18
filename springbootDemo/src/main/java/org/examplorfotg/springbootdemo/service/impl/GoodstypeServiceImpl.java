package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.examplorfotg.springbootdemo.entity.Goodstype;
import org.examplorfotg.springbootdemo.mapper.GoodstypeMapper;
import org.examplorfotg.springbootdemo.service.GoodstypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements GoodstypeService {

    @Resource
    private GoodstypeMapper goodstypeMapper;
    @Override
    public IPage Goodstypepage(IPage<Goodstype> page, Wrapper wrapper) {
        return goodstypeMapper.Goodstypepage(page,wrapper);
    }
}
