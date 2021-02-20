package com.example.demo;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author cwh
 * @date 2020/11/25 14:21
 */
public class StringTest {
    public static void main(String[] args) {
        String a="{MSP_path=/sdfjsdf/sdfsdlaf, chaincode=[{channel=channel01, chaincode=code01}, {channel=channel01, chaincode=code02}, {channel=channel02, chaincode=code03}]}";
        String all = a.replaceAll("=", ":");
        System.out.println(all);
        JSONObject jsonObject = JSONObject.parseObject(all);
        System.out.println(jsonObject);
    }
}
