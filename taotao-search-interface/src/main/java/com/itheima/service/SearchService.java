package com.itheima.service;

import com.itheima.pojo.Item;
import com.itheima.pojo.Page;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   SearchService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/13 14:01
 *  @描述：    TODO
 */
public interface SearchService {


    /**
     * 搜索商品
     * @param q 搜索的关键字
     * @param page  查询的页数
     * @return  封装好的page对象。
     */
    Page<Item> searchItem(String q , int page);
}
