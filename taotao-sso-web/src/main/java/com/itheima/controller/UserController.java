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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Reference
    private UserService userService;


    //http://sso.taotao.com/user/check/{param}/{type}

    //param:表示检查是否存在的值， type : 这个值是什么类型， 1：用户名 2:phone , 3:email

    //http://sso.taotao.com/user/check/abc666/1?r=0.767939886041517&callback=jsonp1540878368187&_=1540879115389
    @RequestMapping("/user/check/{param}/{type}")
    public ResponseEntity<String> check(@PathVariable String param , @PathVariable int type , String callback){

        try {

            System.out.println("要检测用户名是否可用" + param + " : " + type);

            Boolean exist = userService.check(param, type);
            //jsonp1540878368187(false)
            //String result =callback+"("+exist+")";

            String result = "";

            if(!StringUtils.isEmpty(callback)){
                result =callback+"("+exist+")";
            }else{
                result = exist +"";
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }/*
    @RequestMapping("/user/check/{param}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable String param , @PathVariable int type){

        try {

            System.out.println("要检测用户名是否可用" + param + " : " + type);

            Boolean exist = userService.check(param, type);
            return ResponseEntity.ok(exist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(true);
    }*/

    //http://sso.taotao.com/user/fe5cb546aeb3ce1bf37abcb08a40493e


    @GetMapping("/user/{ticket}")
    public ResponseEntity<String> selectUser(@PathVariable String ticket){
        try {
            String result = userService.selectUser(ticket);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("该用户未登录");
        }
    }
}
