package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.mapper.ItemDescMapper;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.Item;
import com.itheima.pojo.ItemDesc;
import com.itheima.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ItemServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/19 9:01
 *  @描述：    TODO
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;


    @Override
    public int addItem(Item item, String desc) {

        //添加item表
        //itemMapper.insert(item); // 添加数据  10个列  ，item(5个值， 5个默认值)
        //itemMapper.insertSelective(item); //添加数据 Selective ： 有选择性

        //从页面传递过来的item还不完整
        long id = (long) (System.currentTimeMillis() + Math.random() * 10000);
        item.setId(id);
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        int result = itemMapper.insertSelective(item);

        //添加desc表
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());


        itemDescMapper.insertSelective(itemDesc);

        return result;
    }

    @Override
    public PageInfo<Item> list(int page, int rows) {

        //必须要设置这一行，才能执行分页查询
        PageHelper.startPage(page , rows);

        List<Item> list = itemMapper.select(null);

        return new PageInfo<Item>(list);
    }
}
