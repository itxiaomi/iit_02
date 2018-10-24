package com.itheima.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.controller
 *  @文件名:   ItemInterfaceController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/23 11:03
 *  @描述：    该conroller主要用于演练restful 风格的接口设计
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemInterfaceController {


    @Reference
    private ItemService itemService;

    //根据商品id来查询商品
    //localhost:8080/taotao/item?id=3
    /*public String getItemById(long id ){

    }*/


    //根据商品id来查询商品
    //localhost:8080/taotao/item/{id}

    //localhost:8080/taotao/item/3

    //@PathVariable 的意思就是当上面的{id} 截取到地址路径的id值之后，赋值给参数id
    //{}里面的文字必须和参数名一样。

    //@RequestMapping(value = "/item/{id}" , method = RequestMethod.GET)

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id ){
        try {
            Item item = itemService.getItemById(id);
            // ResponseEntity.ok(item);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/item")
    public ResponseEntity<Void> addItem(Item item , String desc){

        try {
            int result = itemService.addItem(item,desc);

            System.out.println("新增结果:" + result);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    //locahost:8080/item/3  ---> delete
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id){

        try {
            int result = itemService.deleteItem(id);

            System.out.println("删除结果:" + result);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //地址： localhost:8080/item
    @PutMapping("/item")
    public ResponseEntity<Void> deleteItem(Item item){
        try {
            int result = itemService.updateItem(item);

            System.out.println("更新结果:" + result);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
