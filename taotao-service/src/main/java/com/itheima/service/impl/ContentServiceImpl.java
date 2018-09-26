package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.ContentMapper;
import com.itheima.pojo.Content;
import com.itheima.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public int add(Content content) {
        return contentMapper.insert(content);
    }
}
