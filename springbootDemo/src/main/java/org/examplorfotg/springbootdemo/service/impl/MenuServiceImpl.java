package org.examplorfotg.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.examplorfotg.springbootdemo.entity.Menu;
import org.examplorfotg.springbootdemo.mapper.MenuMapper;
import org.examplorfotg.springbootdemo.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
