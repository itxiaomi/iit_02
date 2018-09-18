package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   ItemCatController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/18 13:53
 *  @描述：    商品分类控制器
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.ItemCat;
import com.itheima.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;


    //默认请求的时候，不带id过来，所以给定一个默认值0 ，表示获取所有的一级分类数据
    @RequestMapping("/rest/item/cat")
    @ResponseBody
    public List<ItemCat> selectItemCat(@RequestParam(defaultValue = "0") long id){

        //查询到所有分类之后，直接抛出去就可以了。
        List<ItemCat> list = itemCatService.selectItemCatByParentId(id);

        System.out.println("list==" + list);

        return list;
    }
}
