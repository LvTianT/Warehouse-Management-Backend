package org.examplorfotg.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.examplorfotg.springbootdemo.entity.Loanregister;
import org.examplorfotg.springbootdemo.entity.Menu;
import org.examplorfotg.springbootdemo.entity.Products;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    IPage Menupage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
