package com.zlikun.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;

/**
 * 对TokenizerMapper进行单元测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 9:00
 */
public class TokenizerMapperTest {

    @Test
    public void mapRecords() throws IOException {

        // 准备输入Text
        Text value = new Text("apache nginx tomcat apache");
        // 设置输入、输出预期，并进行测试
        MapDriver.newMapDriver(new TokenizerMapper())
                .withInput(new LongWritable(0), value)
                .withOutput(new Text("apache"), new IntWritable(1))
                .withOutput(new Text("nginx"), new IntWritable(1))
                .withOutput(new Text("tomcat"), new IntWritable(1))
                .withOutput(new Text("apache"), new IntWritable(1))
                .runTest();

    }

    @Test
    public void ignoreMissingRecords() throws IOException {
        MapDriver.newMapDriver(new TokenizerMapper())
                .withInput(new LongWritable(0), new Text())
                .runTest();
    }

}
