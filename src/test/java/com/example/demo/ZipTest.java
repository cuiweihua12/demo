package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author cwh
 * @date 2020/10/19 17:20
 */
public class ZipTest {


    public static void main(String[] args) throws Exception {
        String path = "E:\\BaiduNetdiskDownload\\1.zip";
        ZipUtil.zip("E:\\1",path);
    }

    public static void example2() throws IOException {
        //文件流的目的，这里可以用outpuStream缓存
        File file = new File("e:\\f.zip");
        FileOutputStream fos = new FileOutputStream(file);
        //指定zip流的输出目的点
        ZipOutputStream zip = new ZipOutputStream(fos);
        //包增加一个文件条目，初始化输入名称
        ZipEntry entry = new ZipEntry("test.txt");
        zip.putNextEntry(entry);
        //输入字节流作为文件条目的来源
        zip.write("a simple txt".getBytes());
        //同上
        ZipEntry entry2 = new ZipEntry("test2.txt");
        zip.putNextEntry(entry2);
        zip.write("a simple file2".getBytes());
        //关闭文件条目的输入点
        zip.closeEntry();
        //关闭zip流
        zip.close();
    }
}
