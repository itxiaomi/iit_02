package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.ItemCatMapper;
import com.itheima.pojo.ItemCat;
import com.itheima.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ItemCatServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/18 13:57
 *  @描述：    TODO
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> selectItemCatByParentId(long parentId) {

       //因为这里是按照普通列来查询的。所以稍微不一样。
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);

        //假设按学生姓名来查询
        /*Student stu = new Stuent();
        stu.setName("zhangsan")
        itemCatMapper.select(stu);*/

        return itemCatMapper.select(itemCat);

    }
}
