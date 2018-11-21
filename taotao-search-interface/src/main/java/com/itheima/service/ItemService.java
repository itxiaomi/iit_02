package com.itheima.service;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   ItemService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/21 8:52
 *  @描述：    TODO
 */
public interface ItemService {

    //添加索引: 该方法仅是针对后台商品改动的时候调用
    void addItem(String message);
}
