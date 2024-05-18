package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Goodstype;

public interface GoodstypeService extends IService<Goodstype> {
    IPage Goodstypepage(IPage<Goodstype> page, Wrapper wrapper);
}
