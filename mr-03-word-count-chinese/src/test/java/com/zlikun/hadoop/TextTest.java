package com.zlikun.hadoop;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 13:33
 */
public class TextTest {

    /**
     * 测试中文乱码处理方法
     */
    @Test
    public void test() throws UnsupportedEncodingException {

        // 默认编码UTF-8
        String origin = "人类其实很孤独。在苍茫的天宇中，虽然有亿万星辰，但是却很难寻到第二颗生命源星。";
        // 转换后的字符串可以正常显示
        Text text = new Text(origin);
        System.out.println(text.toString());


        // 使用GBK编码的中文字符串
        String line = new String(origin.getBytes("GBK"));
        // 转换后字符串变成乱码
        text = new Text(line);
        System.out.println(text.toString());

        // 目前没有什么好的办法，只能将源文件转换为UTF-8编码先

    }

}
