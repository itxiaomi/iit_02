package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.itheima.mapper.ContentMapper;
import com.itheima.pojo.Content;
import com.itheima.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.util.*;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ContentServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 19:04
 *  @描述：    TODO
 */

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Override
    public int add(Content content) {

        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);

       int result = contentMapper.insert(content);

        //删除mysql完毕之后， 也要去删除redis的数据
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");

        return result;
    }

    @Override
    public PageInfo<Content> list(int categoryId, int page, int rows) {


        //设置分页
        PageHelper.startPage(page, rows);

        //查询
        Content content = new Content();

        content.setCategoryId((long) categoryId);
        List<Content> list = contentMapper.select(content);

        return new PageInfo<>(list);
    }

    @Override
    public int edit(Content content) {

        content.setUpdated(new Date());

       int result = contentMapper.updateByPrimaryKeySelective(content);

        //删除mysql完毕之后， 也要去删除redis的数据
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");

        return result;
    }

    @Override
    public int delete(String ids) {  //1  |  35,71

        String[] idArray = ids.split(",");
        int result = 0 ;
        for(String id : idArray){
            result += contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }

        //删除mysql完毕之后， 也要去删除redis的数据
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");

        return result;
    }


    //这里是获取首页的内容，目前仅仅是获取了6张的广告。

    //1. 先从redis里面拿，

    //2. 有就直接返回， 没有。就去查询mysql数据

    //3. 查询完毕，需要把查询到的数据缓存到redis里面，并且返回这份数据给页面显示。

    @Override
    //public List<Content> selectByCategoryId(long cid) {
    public String selectByCategoryId(long cid) {

        System.out.println("============");

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String json = operations.get("bingAd");
        System.out.println("从缓存里面获取广告数据:" + json);
        if(!StringUtils.isEmpty(json)){ //false ： 非空。
            //如果是空。表示缓存没有
            System.out.println("缓存里面有广告的数据，直接返回");
            return json;
        }

        System.out.println("缓存里面没有有广告的数据，要去查询数据库。");

        //如果代码执行到了这里。表示缓存里面没有。要查询数据库了。
        Content c = new Content();
        //由于是按照分类id去查询的，所以一定要给这个对象赋值分类id
        c.setCategoryId(cid);

        //从mysql数据库里面查询出来广告的数据
        List<Content> contents = contentMapper.select(c);

        //自己组拼页面要求的字段信息
        List<Map<String , Object>> list = new ArrayList<>();
        //把从数据库查询出来的集合，遍历，一个content就对应一个map集合
        for(Content content :contents){
            Map<String , Object> map = new HashMap<>();
            map.put("src" , content.getPic());
            map.put("width",670);
            map.put("height" , 240);
            map.put("href",content.getUrl());

            list.add(map);
        }

        //把这个list集合变成json字符串然后存进去。
        json = new Gson().toJson(list);

        //存到redis里面去。
        operations.set( "bingAd", json);

        System.out.println("从mysql查询出来的数据要存到redis数据库去");

        return json;
    }









    /*@Override
    public List<Content> selectByCategoryId(long cid) {

        Content content = new Content();
        //由于是按照分类id去查询的，所以一定要给这个对象赋值分类id
        content.setCategoryId(cid);

        return contentMapper.select(content);
    }*/
}
