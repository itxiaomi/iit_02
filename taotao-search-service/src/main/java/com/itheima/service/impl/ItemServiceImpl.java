package com.itheima.service.impl;

import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.Item;
import com.itheima.service.ItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   ItemServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/21 8:53
 *  @描述：    TODO
 */

@Service
public class ItemServiceImpl implements ItemService{


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrClient solrClient;

    //收到的消息其实就是商品的id值。
    @JmsListener(destination = "item-save")
    @Override
    public void addItem(String message) {

        try {
            System.out.println("收到的 message=" + message);

            //根据id查询商品数据
            long id = Long.parseLong(message);
            Item item = itemMapper.selectByPrimaryKey(id);

            System.out.println("查询到的商品是：" + item);
            // 2. 把商品数据添加到索引库。

            SolrInputDocument doc  =new SolrInputDocument();

            doc.addField("id" , item.getId());
            doc.addField("item_title" , item.getTitle());
            doc.addField("item_image" , item.getImage());
            doc.addField("item_price" , item.getPrice());
            doc.addField("item_cid" , item.getCid());
            doc.addField("item_status" , item.getStatus());


            UpdateResponse response = solrClient.add(doc);

            System.out.println("aaa="+response.toString());

            solrClient.commit();

            System.out.println("更新索引库成功");

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
