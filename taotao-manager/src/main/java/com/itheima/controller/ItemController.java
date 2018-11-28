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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/*/ restful api 风格
@DeleteMapping  ： 删除
@PutMapping  ：更新
@PostMapping  ： 添加
@GetMapping :  查询*/

@Controller
public class ItemController {

    private ObjectMapper mapper = new ObjectMapper();

    @Reference
    private ItemService itemService;

    // 添加商品的时候，大部分的内容都会装载到item对象里面去 ，然后item对象要添加到item表里面去
    //商品的描述，使用desc 来接收，然后要添加到item_desc这张表
    @RequestMapping(value = "/rest/item" , method = RequestMethod.POST)
    @ResponseBody
    public String addItem(Item item , String desc) throws JsonProcessingException {

       int result =  itemService.addItem(item , desc);

        List<Item> list = new ArrayList<>();
        item.setId(123L);
        list.add(item);

        String s = mapper.writeValueAsString(list);

        String s2 = list.toString();


        System.out.println("s===" + s);
        System.out.println("s2===" + s2);

        return "success";
    }


    @RequestMapping(value = "/rest/item" , method = RequestMethod.GET)
    @ResponseBody
    public  Map<String  , Object> list(int page , int rows){

        PageInfo<Item> pageInfo = itemService.list(page, rows);

        //easyui 的数据表格显示数据，要求有固定格式，json里面有 total属性和 rows属性
        //total 属性表示总共有多少条记录 ，
        //rows 属性表示当前这一页的集合数据，也就是list集合

        Map<String  , Object> map = new HashMap<>();
        map.put("total" , pageInfo.getTotal());
        map.put("rows" , pageInfo.getList());


        return map;
    }


}
