package org.examplorfotg.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.examplorfotg.springbootdemo.entity.User;

public interface UserService extends IService<User> {
//    IPage pageC(IPage<User> page);

    IPage userpage(IPage<User> page, Wrapper wrapper);
    long userRegister(String userAccount, String userPassword, String checkPassword,String email);
}
