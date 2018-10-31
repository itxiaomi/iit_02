package com.itheima.service;

import com.itheima.pojo.User;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   UserService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/11 11:14
 *  @描述：    TODO
 */
public interface UserService {

    Boolean check(String param , int type);

    String selectUser(String ticket);

    /**
     * 注册用户
     */
    int addUser(User user);

    String login(User user);
}
