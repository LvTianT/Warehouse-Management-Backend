package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.examplorfotg.springbootdemo.entity.Products;
import org.examplorfotg.springbootdemo.mapper.ProductsMapper;
import org.examplorfotg.springbootdemo.service.ProductsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductsService {
    @Resource
    private ProductsMapper goodsMapper;
    @Override
    public IPage pageCC(IPage<Products> page, Wrapper wrapper) {
        return goodsMapper.pageCC(page,wrapper);
    }
}
