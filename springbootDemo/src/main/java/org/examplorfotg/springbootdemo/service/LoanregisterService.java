package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Loanregister;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Products;


public interface LoanregisterService extends IService<Loanregister> {
    IPage pageCC(IPage<Loanregister> page, Wrapper wrapper);
}
