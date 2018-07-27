package com.zlikun.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * 对IntSumReducer进行单元测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 9:09
 */
public class IntSumReducerTest {

    @Test
    public void reduceRecords() throws IOException {
        // 设置输入、输出预期，并进行测试
        ReduceDriver.newReduceDriver(new IntSumReducer())
                .withInput(new Text("apache"), Arrays.asList(new IntWritable(1), new IntWritable(1)))
                .withOutput(new Text("apache"), new IntWritable(2))
                .runTest();
    }

}
