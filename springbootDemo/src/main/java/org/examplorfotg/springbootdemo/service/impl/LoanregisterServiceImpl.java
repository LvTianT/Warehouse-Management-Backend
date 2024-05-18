package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.examplorfotg.springbootdemo.entity.Loanregister;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.mapper.LoanregisterMapper;
import org.examplorfotg.springbootdemo.mapper.UsertransactionsMapper;
import org.examplorfotg.springbootdemo.service.LoanregisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LoanregisterServiceImpl extends ServiceImpl<LoanregisterMapper, Loanregister> implements LoanregisterService {
    @Resource
    private LoanregisterMapper loanregisterMapper;
    @Override
    public IPage Loanregisterpage(IPage<Loanregister> page, Wrapper wrapper) {
        return loanregisterMapper.Loanregisterpage(page,wrapper);
    }
}
