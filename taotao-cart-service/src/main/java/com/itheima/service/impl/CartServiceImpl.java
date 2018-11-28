package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.Cart;
import com.itheima.pojo.Item;
import com.itheima.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    //把商品添加到购物车中
    @Override
    public void addItemToCart(long userId, long id, int num) {

        System.out.println("要添加商品到购物车了");

        //1. 先查询已经购物车的商品
        List<Cart> cartList = queryCartByUserId(userId);


        //2. 遍历上面的购物车集合，看看里面的商品是否和现在要添加进来的商品一样，
        //如果是一样的商品，那么就让这个商品的购买数量 + 1
        //如果不一样，那么就去查询数据库，或者这个商品的数据，然后追加到cartList中

        Cart c = null;

        //假如购物车中有10件商品
        for (Cart cart : cartList) {
            //购物车里面有这件商品
            if(cart.getItemId() == id){
                c = cart;
                break;
            }
        }

        //判断是否有一样的商品
        if(c != null){
            //有这个商品
            c.setNum(c.getNum() + num );
            c.setUpdate(new Date());
        }else{
            //没有这个商品  --- 去查询数据库，获取这个商品，然后追加到list集合中
            Item item = itemMapper.selectByPrimaryKey(id);

            Cart cart = new Cart();
            cart.setItemId(id);
            cart.setItemTitle(item.getTitle());
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setCreate(new Date());
            cart.setUpdate(new Date());
            cart.setUserId(userId);
            cart.setNum(num);

            cartList.add(cart);
        }


        //把这个list集合保存到redis中。
        String json = new Gson().toJson(cartList);

        System.out.println("现在购物车的商品有:" + json);

        redisTemplate.opsForValue().set("iitcart_"+userId , json);

    }


    //根据用户的id查询它对应的购物车数据
    @Override
    public List<Cart> queryCartByUserId(long userId) {

        //[{} , {} , {} ] === list<cart>

        // {"title":"ipad4" , "number" : 4} --- cart

        //1.根据用户id查询redis，获取以前的购物车数据，是一个json数据
        String json = redisTemplate.opsForValue().get("iitcart_"+userId);

        if(!StringUtils.isEmpty(json)){
            //2. 把json字符串转化成list<Cart>
            List<Cart> cartList = new Gson().fromJson(json,  new TypeToken<List<Cart>>(){}.getType());

            return cartList;
        }

        //如果是空，表示是第一次来添加商品到购物车，所以redis里面不会有任何数据
        return new ArrayList<Cart>();
    }

    static class Teacher{
        private String name;
        private int age ;


        public Teacher() {
        }

        public Teacher(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String [] args){



        List<Teacher> list = new ArrayList<>();
        list.add(new Teacher("张三1",18));
        list.add(new Teacher("张三2",28));
        list.add(new Teacher("张三3",38));

        String json = new Gson().toJson(list);
        System.out.println("json=" + json);


        //把一个json数据转化成list集合怎么办？

        List<Teacher> list2 = new Gson().fromJson(json,  new TypeToken< List < Teacher >>(){}.getType());

        System.out.println("list2=" + list2.get(0).getName());


    }

}
