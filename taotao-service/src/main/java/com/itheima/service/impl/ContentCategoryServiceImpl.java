package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.ContentCategoryMapper;
import com.itheima.pojo.ContentCategory;
import com.itheima.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ContentCategoryServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 9:05
 *  @描述：    TODO
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper mapper;

    @Override
    public List<ContentCategory> getCategoryByParentId(Long id) {


        ContentCategory category = new ContentCategory();

        category.setParentId(id);


        return mapper.select(category);

    }
}
