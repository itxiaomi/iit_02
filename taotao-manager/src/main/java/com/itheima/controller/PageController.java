package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   PageController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/18 10:29
 *  @描述：    TODO
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    /*@RequestMapping("index")
    public String index(){

        System.out.println("要显示首页了~！");

        *//*
            如果使用了SpringMVC ，需要指定资源路径所在。

            如果使用了SpringBoot, 那么默认跳转的位置是在
            resource/static | public | xxx | xxx  | templates

            现在的资源在/web-inf/view/

            spring.mvc.view.prefix=/WEB-INF/view/

            spring.mvc.view.suffix=.jsp


            最终的跳转位置就是：/WEB-INF/view/index.jsp
         *//*

        return "index";
    }*/



    /*
        rest/page/{pageName}   :  {pageName}  使用于截取后面的字符
        @PathVariable String pageName :  把上面截取到的字符，赋值给参数pageName
        有一个需要注意的地方就是： {}里面的字符  和参数名称必须一致。
     */

    //@RequestMapping("/rest/page/item-add")
    @RequestMapping("/rest/page/{pageName}")
    public String page(@PathVariable String pageName){

        System.out.println("pageName=" + pageName);

        return pageName;
    }

/*
    @RequestMapping("/rest/page/item-add")
    public String ItemAdd(){

        return "item-add";
    }

    @RequestMapping("/rest/page/item-list")
    public String ItemList(){

        return "item-list";
    }*/
}
