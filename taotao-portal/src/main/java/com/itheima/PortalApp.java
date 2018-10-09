package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima
 *  @文件名:   PortalApp
 *  @创建者:   xiaomi
 *  @创建时间:  2018/9/25 13:48
 *  @描述：    TODO
 */
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class PortalApp {

    public static void main(String [] args){
        SpringApplication.run(PortalApp.class , args);
    }
}
