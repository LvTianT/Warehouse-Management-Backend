package org.examplorfotg.springbootdemo.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.examplorfotg.springbootdemo.entity.Products;


@Mapper
public interface ProductsMapper extends BaseMapper<Products> {
    IPage pageCC(IPage<Products> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
