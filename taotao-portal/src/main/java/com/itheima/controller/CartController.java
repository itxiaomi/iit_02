package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   CartController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/28 11:16
 *  @描述：    TODO
 */

@Controller
public class CartController {


    //添加商品到购物车
    @RequestMapping("/cart/add/{id}.html")
    public String addToCart(@PathVariable long id , int num){

        return null;
    }
}
