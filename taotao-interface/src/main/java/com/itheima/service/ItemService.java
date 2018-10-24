package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.pojo.Item;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   ItemService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/19 9:00
 *  @描述：    TODO
 */
public interface ItemService {

    int addItem(Item item, String desc);

    PageInfo<Item> list(int page , int rows);

    Item getItemById(long id);


    int deleteItem(long id);

    int updateItem(Item item);


}
