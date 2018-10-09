package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.pojo.Content;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service
 *  @文件名:   ContentService
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 19:03
 *  @描述：    TODO
 */
public interface ContentService {

    int add(Content content);


    PageInfo<Content> list(int categoryId, int page , int rows);


    int edit(Content content);

    int delete(String ids);


    List<Content>  selectByCategoryId(long cid);
}