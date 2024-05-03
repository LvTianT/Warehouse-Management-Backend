package org.examplorfotg.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.examplorfotg.springbootdemo.entity.Menu;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}
