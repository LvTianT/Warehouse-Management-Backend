package org.examplorfotg.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.examplorfotg.springbootdemo.entity.Inventorycheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface InventorycheckMapper extends BaseMapper<Inventorycheck> {
    IPage pageCC(IPage<Inventorycheck> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
