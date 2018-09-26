package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   ContentController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/26 19:02
 *  @描述：    TODO
 */


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Content;
import com.itheima.service.ContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/rest/content")
    public String add(Content content){
        contentService.add(content);

        return "success";
    }

}
