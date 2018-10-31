package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.itheima.pojo.User;
import com.itheima.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   IndexController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/25 13:54
 *  @描述：    TODO
 */
@Controller
public class IndexController {


    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @Reference
    private ContentService contentService;


    //www.taotao.com/register.jsp
    @RequestMapping("/page/{pageName}")
    public String page(@PathVariable  String pageName){
        return pageName;
    }


    //www.taotao.com
    @RequestMapping("/")
    public String index(Model model , HttpServletRequest request){

        System.out.println("要获取首页的广告数据了~");


        //在这里获取ticket， 然后到redis里面去查询用户数据，然后放到页面显示就好了。
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            // JSESSIONID=8F793A6BB86B68FE714B4DA447A54FFD; tciket=itt02_cc20e370-c1ff-4a82-99d1-692022a15095
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                System.out.println("name=" + name);
                if("ticket".equals(name)){
                    String key = cookie.getValue();
                    //{"username":zhangsan,"pas"}
                    String userInfo =redisTemplate.opsForValue().get(key);

                    User user = new Gson().fromJson(userInfo , User.class);

                    model.addAttribute("user",user);

                    break;
                }
            }
        }



        //要把大广告位的6张图片给查询出来。
        int categoryId = 89;

        //这里查询回来的是集合，里面装的是content对象，但是页面显示6张图片，要求的数据格式不是这样的。
        /*
        [
            {
                "srcB": "http://image.taotao.com/group1/M00/00/00/wKjjg1u8tJqAKFGsAABO1RReHyk883.jpg",
                "height": 240,
                "alt": "",
                "width": 670,
                "src": "http://image.taotao.com/group1/M00/00/00/wKjjg1u8tJqAKFGsAABO1RReHyk883.jpg",
                "widthB": 550,
                "href": "http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE",
                "heightB": 240
            },
            {},
            {}
            ]
         */
         String json = contentService.selectByCategoryId(categoryId);

        System.out.println("json=" + json);

         /*List<Map<String , Object>> list = new ArrayList<>();

         //把从数据库查询出来的集合，遍历，一个content就对应一个map集合
         for(Content content :contents){
             Map<String , Object> map = new HashMap<>();
             map.put("src" , content.getPic());
             map.put("width",670);
             map.put("height" , 240);
             map.put("href",content.getUrl());

             list.add(map);
         }*/


         //把 list ----Gson  | Fastjson ----> json字符串转化

       // String json = new Gson().toJson(list);

         model.addAttribute("list" , json);



        //这里的index仅仅起到的是跳转页面
        return "index";
    }


}
