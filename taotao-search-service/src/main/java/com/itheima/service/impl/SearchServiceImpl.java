package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.Item;
import com.itheima.pojo.Page;
import com.itheima.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   SearchServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/11/13 14:03
 *  @描述：    TODO
 */

@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private SolrClient solrClient;

    //这个方法的数据来自于索引库。
    @Override
    public Page<Item> searchItem(String q, int page) {
        try {




            SolrQuery query = new SolrQuery();

            //设置查询条件
            query.setQuery("item_title:"+q);

            //设置分页
            query.setStart((page - 1 ) * 16); //忽略前面的多少条
            query.setRows(16); //每页返回多少条


            //查询到当前页的数据
            QueryResponse response = solrClient.query(query);


            //页面需要：  查询的关键字 ， itemlist  ， page ，totalpage

            SolrDocumentList results = response.getResults();

            //总记录数
            long total = results.getNumFound();


            //TODO: 需要封装当前这一页的数据。


            System.out.println("total=" + total);

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
