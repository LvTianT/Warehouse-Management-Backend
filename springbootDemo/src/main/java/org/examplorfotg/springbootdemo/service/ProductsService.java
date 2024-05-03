package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.Products;

public interface ProductsService extends IService<Products> {
    IPage pageCC(IPage<Products> page, Wrapper wrapper);
}
