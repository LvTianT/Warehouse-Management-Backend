package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UsertransactionsService extends IService<Usertransactions> {
    IPage Usertransactionspage(IPage<Usertransactions> page, Wrapper wrapper);
}
