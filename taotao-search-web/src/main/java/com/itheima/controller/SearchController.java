package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   SearchController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/13 13:42
 *  @描述：    TODO
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Item;
import com.itheima.pojo.Page;
import com.itheima.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;



    /*
        ${query} ： 查询的关键字

        ${page} : 当前这一页的相关数据

        ${totalPages} : 总页数

        ${page} ： 当前页

     */
    @RequestMapping("/search")
    public String search(Model model , String q , @RequestParam(defaultValue = "1") int page){

        System.out.println("要跳转到搜索页面了 ==="+q);

        Page<Item> pageItem = searchService.searchItem(q, page);

        //  ${query} ： 查询的关键字
        model.addAttribute("query",q);

        // ${page} : 当前这一页的相关数据
        model.addAttribute("page",pageItem);

        //${totalPages} : 总页数
        model.addAttribute("totalPages",pageItem.getLast());

        // ${page} ： 当前页
       // model.addAttribute("page",page);

        return "search";
    }
}
