package com.example.demo;

import com.example.demo.SSL.RsaEncrypt;
import com.sun.deploy.net.URLEncoder;
import lombok.SneakyThrows;


/**
 * @author cwh
 * @date 2020/10/9 17:01
 */
public class Demo {
    @SneakyThrows
    public static void main(String[] args) {
       /* System.out.println(0/10);*/
        //时间戳
       /* Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(timestamp);
        System.out.println(timestamp.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String format = simpleDateFormat.format(timestamp.getTime());
        System.out.println(format);*/
        //加密明文
        String str = "崔蔚华";
        RsaEncrypt rsaEncrypt = new RsaEncrypt();
        //加密
        byte[] encrypt = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), str.getBytes());
        System.out.println("密文："  +new String(encrypt));
        //解密
        byte[] decrypt = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), encrypt);
        System.out.println("明文："  +new String(decrypt));
        //加签
        byte[] bytes = rsaEncrypt.rsaSign(str, rsaEncrypt.getPrivateKey());
        System.out.println("加签："  +new String(bytes));
        //验签
        boolean b = rsaEncrypt.doCheck(str, bytes, rsaEncrypt.getPublicKey());
        System.out.println("验签："+b);

        System.out.println(URLEncoder.encode("name=cuiw&age=12","UTF-8"));
        System.out.println(URLEncoder.encode("name=cuiw&age=12","UTF-8"));
    }


}
