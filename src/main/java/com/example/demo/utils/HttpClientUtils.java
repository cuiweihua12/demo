package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>Title: httpClients 工具类</p>
 * <p>Description: </p>
 * @author cwh
 * @date 2021/2/20 10:57
 */
public class HttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    @SneakyThrows
    public static void main(String[] args) {
        String uri = "http://www.itnews.icu:8886/revice";
        String response = doGet(uri);
        JSONObject jsonObject = JSONObject.parseObject(response);
        System.out.println(jsonObject.get("msg"));
        System.out.println(response);
    }

    /**
     * GET---无参
     * @param uri 访问呢路径
     */
    public static String doGet(String uri) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        HttpEntity responseEntity = null;

        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            responseEntity = response.getEntity();
            log.info("响应状态为:{}" , response.getStatusLine());
            String responseStr = EntityUtils.toString(responseEntity);
            log.info("响应内容长度为:{}" , responseEntity.getContentLength());
            log.info("响应内容为:" + responseStr);
            return responseStr;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return null;
    }

    /**
     * 功能描述: GET请求<br>
     * @Param: uri 请求路径
     * @param  params 请求参数 name=value1&age=18 格式
     * @param encode 参数编码格式 默认UTF-8
     * @Return: 请求响应
     * @Date: 2021/2/20 15:35
     **/
    public static String doGetParams(String uri,String params,String encode) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        try {
            params = URLEncoder.encode(params,encode==null?"UTF-8":encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /*StringBuffer params = new StringBuffer();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            params.append("name=" + URLEncoder.encode("&", "utf-8"));
            params.append("&");
            params.append("age=24");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }*/

        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri + "?" + params);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            String entity = EntityUtils.toString(responseEntity);
            log.info("响应状态为:{}" , response.getStatusLine());
            log.info("响应内容长度为:{}",responseEntity.getContentLength());
            log.info("响应内容为:{}" , entity );
            return entity;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
