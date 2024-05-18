package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.examplorfotg.springbootdemo.entity.Menu;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.entity.User;
import org.examplorfotg.springbootdemo.entity.Usertransactions;
import org.examplorfotg.springbootdemo.mapper.MenuMapper;
import org.examplorfotg.springbootdemo.mapper.ProductsMapper;
import org.examplorfotg.springbootdemo.mapper.UsertransactionsMapper;
import org.examplorfotg.springbootdemo.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Override
    public IPage Menupage(IPage<Menu> page, Wrapper wrapper) {
        return  menuMapper.Menupage(page, wrapper);
    }

}
