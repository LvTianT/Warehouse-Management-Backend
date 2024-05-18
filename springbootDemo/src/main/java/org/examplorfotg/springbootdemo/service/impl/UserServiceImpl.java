package org.examplorfotg.springbootdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.examplorfotg.springbootdemo.common.BusinessMsgEnum;
import org.examplorfotg.springbootdemo.entity.User;
import org.examplorfotg.springbootdemo.exception.BusinessException;
import org.examplorfotg.springbootdemo.mapper.UserMapper;
import org.examplorfotg.springbootdemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    private static final String SALT = "lv";

    @Override
    public IPage userpage(IPage<User> page, Wrapper wrapper) {
        return userMapper.userpage(page, wrapper);
    }


    public long userRegister(String no, String password, String checkPassword, String email) {
        // 1. 校验
        if (StringUtils.isAnyBlank(no, password, checkPassword, email)) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "参数为空");
        }
        if (no.length() < 4) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "账号不能小于4位！");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "密码不能小于8位！");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(no);
        if (matcher.find()) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "账户不能包含特殊字符!");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "两次密码不一致!");
        }
        //校验邮箱格式
        String validEmail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        Matcher emailmatcher = Pattern.compile(validEmail).matcher(email);
        if (email.length() < 6 || !emailmatcher.find()) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "邮箱格式不正确!");
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("no", no);
        queryWrapper.eq("email", email);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException( BusinessMsgEnum.PARAMS_ERROR, "账号已存在!");
        }
        // 2. 加密
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setNo(no);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoleId(0);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }
}
