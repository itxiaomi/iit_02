package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   ItemController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/19 8:56
 *  @描述：    这是有关商品处理的控制器
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Reference
    private ItemService itemService;

    // 添加商品的时候，大部分的内容都会装载到item对象里面去 ，然后item对象要添加到item表里面去
    //商品的描述，使用desc 来接收，然后要添加到item_desc这张表
    @RequestMapping("/rest/item")
    @ResponseBody
    public String addItem(Item item , String desc){

       int result =  itemService.addItem(item , desc);

        System.out.println("result===" + result);

        return "success";
    }

}
