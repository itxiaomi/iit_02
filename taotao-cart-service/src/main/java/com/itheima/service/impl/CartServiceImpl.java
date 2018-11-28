package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.Cart;
import com.itheima.service.CartService;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   CartServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/28 11:26
 *  @描述：    TODO
 */

@Service
public class CartServiceImpl implements CartService {

    @Override
    public void addItemToCart(long userId, long id, int num) {

    }

    @Override
    public List<Cart> queryCartByUserId(long userId) {
        return null;
    }
}
