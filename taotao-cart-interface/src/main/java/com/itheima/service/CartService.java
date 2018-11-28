package com.itheima.service;

import com.itheima.pojo.Cart;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   CartService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/28 11:21
 *  @描述：    TODO
 */
public interface CartService {

    //添加商品到购物车

    /**
     * 添加商品到购物车该方法主要用于把商品添加到redis数据库，所以这里必须要表明是谁添加的。
     * 如果在没有登录的情况，加入购物车，那么直接使用cookie来添加的商品数据即可
     * @param userId
     * @param id
     * @param num
     */
    void addItemToCart(long userId , long id , int num);


    /**
     * 根据用户的id查询购物车数据。如果是未登录的情况，要查看购物车，那么不需要调用
     * 这个方法，只需要获取cookie的数据到购物车页面显示即可。
     * @param userId
     * @return
     */
    List<Cart> queryCartByUserId(long userId);
}
