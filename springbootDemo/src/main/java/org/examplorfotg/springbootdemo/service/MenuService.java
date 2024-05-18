package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Menu;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.entity.User;

public interface MenuService extends IService<Menu> {
    IPage Menupage(IPage<Menu> page, Wrapper wrapper);
}
