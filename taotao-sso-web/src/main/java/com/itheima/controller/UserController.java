package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   UserController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/24 18:40
 *  @描述：    TODO
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Reference
    private UserService userService;


    //http://sso.taotao.com/user/check/{param}/{type}

    //param:表示检查是否存在的值， type : 这个值是什么类型， 1：用户名 2:phone , 3:email

    @RequestMapping("/user/check/{param}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable String param , @PathVariable int type){

        try {
            Boolean exist = userService.check(param, type);
            return ResponseEntity.ok(exist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(true);
    }
}
