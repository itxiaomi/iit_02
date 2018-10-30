package com.itheima.test;

import com.google.gson.Gson;
import com.itheima.pojo.User;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Date;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.test
 *  @文件名:   SsoTest
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/30 10:33
 *  @描述：    TODO
 */
public class SsoTest {

    @Test
    public void testTicket(){

        User user = new User();
        user.setId(12L);
        user.setEmail("aa@aa.cc");
        user.setPhone("10086");
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setCreated(new Date());
        user.setUpdated(new Date());


        String json =new Gson().toJson(user);

        Jedis jedis = new Jedis("192.168.227.132" ,7002 );
        jedis.set("iit_abc123",json);
        jedis.close();


    }

}
