package com.itheima.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.test
 *  @文件名:   TestHttpClient
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/24 8:45
 *  @描述：    TODO
 */
public class TestHttpClient {


    @Test
    public void testDemo() throws IOException {

        //创建一个http的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //构建get请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        //发起请求
        HttpResponse response = httpClient.execute(httpGet);


        int code = response.getStatusLine().getStatusCode();

        if(200 == code){
            //获取响应实体
            HttpEntity entity = response.getEntity();

            String content = EntityUtils.toString(entity , "utf-8");

            System.out.println("content=" + content);
        }

        //关闭客户端
        httpClient.close();
    }


    @Test
    public void testGet() throws IOException{

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "http://admin.taotao.com/item/1030175999";
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        String content = EntityUtils.toString(entity , "utf-8");

        System.out.println("content=" + content);

        httpClient.close();
    }



    //这个方法仅仅针对以前的那种API接口，如果是restful风格的接口，参数是直接在地址上面拼的。
    @Test
    public void testGetParam() throws Exception{

        CloseableHttpClient httpClient = HttpClients.createDefault();

        //String url = "http://admin.taotao.com/item/1030175999";
        //String url = "http://admin.taotao.com/item";

        String url = "http://admin.taotao.com//rest/content/category";

        //对地址进行再一次封装，以便携带数据
        URIBuilder uriBuilder = new URIBuilder(url);

        //添加参数
        uriBuilder.addParameter("id" , "0");

        URI uri = uriBuilder.build();

        System.out.println("uri=" + uri.toString());

        HttpGet httpGet = new HttpGet(uri);

        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();

        String content = EntityUtils.toString(entity , "utf-8");

        System.out.println("content=" + content);

        httpClient.close();
    }


    @Test
    public void testPost() throws IOException{


        CloseableHttpClient httpClient = HttpClients.createDefault();


        String url ="http://admin.taotao.com/item";

        HttpPost httpPost = new HttpPost(url);

        //给post请求携带参数

        List<NameValuePair> paramters = new ArrayList<>();


        //给post请求添加参数
        paramters.add(new BasicNameValuePair("title","iphonexxx"));
        paramters.add(new BasicNameValuePair("desc","超级贵"));
        paramters.add(new BasicNameValuePair("sellPoint","超贵的卖点"));
        paramters.add(new BasicNameValuePair("price","19999"));
        paramters.add(new BasicNameValuePair("num","200"));
        paramters.add(new BasicNameValuePair("cid","76"));

        //对传递的参数进行url编码，使用的是UTF-8字符集
        HttpEntity entity = new UrlEncodedFormEntity(paramters ,"utf-8");

        //给post请求设置实体
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);

        if(response.getStatusLine().getStatusCode() == 200){

            String content = EntityUtils.toString(response.getEntity(), "utf-8");

            System.out.println("content=" + content);

        }

        httpClient.close();

    }



    @Test
    public void testDelete() throws IOException{


        CloseableHttpClient httpClient = HttpClients.createDefault();


        String url = "http://admin.taotao.com/item/1540347824852";
        HttpDelete httpDelete = new HttpDelete(url);


        HttpResponse response = httpClient.execute(httpDelete);

        if(200 == response.getStatusLine().getStatusCode()){

            String content = EntityUtils.toString(response.getEntity() , "utf-8");

            System.out.println("content=" + content);


        }

        httpClient.close();
    }



    @Test
    public void testPut() throws IOException{


        CloseableHttpClient httpClient = HttpClients.createDefault();


        String url = "http://admin.taotao.com/item";
        HttpPut httpPut = new HttpPut(url);

        //封装传递的数据，
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("id", "1538962455895"));
        list.add(new BasicNameValuePair("title", "bbb"));

        //对数据进行url编码 ，使用utf-8字符集
        HttpEntity entity = new UrlEncodedFormEntity(list , "utf-8");

        //给这次请求设置携带的数据
        httpPut.setEntity(entity);


        HttpResponse response = httpClient.execute(httpPut);

        if(200 == response.getStatusLine().getStatusCode()){

            String content = EntityUtils.toString(response.getEntity() , "utf-8");

            System.out.println("content=" + content);


        }

        httpClient.close();
    }




}
