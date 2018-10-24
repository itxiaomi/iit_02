package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   UserServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/24 18:47
 *  @描述：    TODO
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检查给定的参数是否存在
     * @param param
     * @param type
     * @return true:表示存在，false:表示不存在
     */
    @Override
    public Boolean check(String param, int type) {

        User user = new User();

        switch (type) {
            case 1: //用户名
                user.setUsername(param);
                break;
            case 2: //电话
                user.setPhone(param);
                break;
            case 3: //邮箱
                user.setEmail(param);
                break;
            default:
                user.setUsername(param);
                break;
        }

        List<User> list = userMapper.select(user);

        return list.size() > 0 ;
    }
}
