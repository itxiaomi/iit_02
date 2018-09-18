package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   UserController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/11 11:03
 *  @描述：    TODO
 */

import com.github.pagehelper.PageInfo;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   // = @Controller +@ResponseBody (表示方法的返回值都是json字符串)
//@Controller
public class UserController {

    //多家一行注释  热部署

    //这个注解的意思是 ： 自动注入，在当前这个项目的Spring容器里面寻找UserService这个接口的实现类对象。
    //找到之后，给这个userService 赋值进来。 UserService的实现类是UserServiceImpl
    //@Autowired


   // @Reference
    private UserService userService ;


    @RequestMapping("save")
    public String save(){

        System.out.println("调用了UserController的save方法~！");

        userService.save();
        return "save success~!";
    }


    @RequestMapping("selectOne")
    public User selectOne(){
        User user = userService.selectOne(7);
        return user;
    }


    @RequestMapping("findByPage")
    public PageInfo<User> findByPage(int currentPage , int pageSize){
        return userService.findByPage(currentPage , pageSize);
    }

}
