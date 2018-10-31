package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   UserController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/31 8:42
 *  @描述：    用户的注册
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Reference
    private UserService userService;


    @PostMapping("/user/doRegister.shtml")
    @ResponseBody
    public  Map<String  ,String> register(User user){

        System.out.println("user=" + user);

        int result = userService.addUser(user);

        Map<String  ,String> map = new HashMap<>();
        if(result > 0 ){
            map.put("status","200");
        }else{
            map.put("status","500");
        }

        return map;
    }


    /*
        1. 登录成功之后，生成一个唯一的key(ticket) ,把这个key和用户的数据保存在redis数据库里面

        2. 为了保证下次直接添加到购物车，能够知道是谁完成的操作，需要从redis里面获取用户的信息。
        要想获取用户的信息，必须拥有key(ticket).  把这个key 写到cookie里面去，传输给客户端（浏览器）

    http://www.taotao.com/user/doLogin.shtml --> cookie : path

    http://www.taotao.com/user/
        cookiet

     http://www.taotao.com   /

     */
    @PostMapping("/user/doLogin.shtml")
    @ResponseBody
    public  Map<String ,String> login(User user , HttpServletResponse response){

        Map<String ,String> map = new HashMap<>();

        //ticket就是redis里面保存用户数据的key
        String ticket = userService.login(user);

        //ticket不是空，表示登录成功，并且也存到了redis里面
        if(!StringUtils.isEmpty(ticket)){

            //把用户登录过后的凭证放到cookie里面。
            Cookie cookie = new Cookie("ticket",ticket);
            //cookie有效期是7天
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);

            map.put("status","200");
            //表示登录成功，跳转的位置
            map.put("success","http://www.taotao.com");

            return map;
        }

        map.put("status","500");

        //登录成功，就回到index页面
        return map;


    }
}
