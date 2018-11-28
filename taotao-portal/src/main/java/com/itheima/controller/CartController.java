package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Cart;
import com.itheima.pojo.User;
import com.itheima.service.CartService;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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



    @Reference
    private CartService cartService;

    @Autowired
    private RedisTemplate<String , String> template;

    //添加商品到购物车
    @RequestMapping("/cart/add/{id}.html")
    public String addToCart(@PathVariable long id , int num , HttpServletRequest request){


        //从cookie里面获取用户的登录凭证
        String ticket = CookieUtil.findTicket(request);


        //如果凭证部位空，表示已经你登录了。
        if(ticket != null){

            //根据ticket去redis中获取用户数据
            User user = RedisUtil.findUserByTicket(template, ticket);

            //把商品添加到购物车里面去。 登录的分支
            cartService.addItemToCart(user.getId() , id , num);
        }else{
            //没有登录
        }

        //要去购物车页面显示数据了。
        return "cartSuccess";
    }


    //cart/cart.html
    @RequestMapping("/cart/cart.html")
    public String showCart(HttpServletRequest request , Model model){

        //1. 获取用户数据
        //从cookie里面获取用户的登录凭证
        String ticket = CookieUtil.findTicket(request);

        //2. 到redis中获取购物车的数据
        //如果凭证部位空，表示已经你登录了。
        if(ticket != null){

            //根据ticket去redis中获取用户数据
            User user = RedisUtil.findUserByTicket(template, ticket);

            List<Cart> cartList = cartService.queryCartByUserId(user.getId());

            model.addAttribute("cartList" , cartList);

        }else{
            //没有登录
        }

        //3. 存储到model里面。
        return "cart";
    }

}
