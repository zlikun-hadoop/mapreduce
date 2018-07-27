package com.zlikun.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 11:33
 */
public class NcdcReducerTest {

    @Test
    public void returnMaximumIntegerInValues() throws IOException {
        ReduceDriver.newReduceDriver(new NcdcReducer())
                .withInput(new Text("1950"), Arrays.asList(new IntWritable(10), new IntWritable(5)))
                .withOutput(new Text("1950"), new IntWritable(10))
                .runTest();
    }

}
