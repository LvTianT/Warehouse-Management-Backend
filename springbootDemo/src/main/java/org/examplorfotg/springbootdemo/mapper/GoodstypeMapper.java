package org.examplorfotg.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.examplorfotg.springbootdemo.entity.Goodstype;

@Mapper
public interface GoodstypeMapper extends BaseMapper<Goodstype> {
    IPage Goodstypepage(IPage<Goodstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
