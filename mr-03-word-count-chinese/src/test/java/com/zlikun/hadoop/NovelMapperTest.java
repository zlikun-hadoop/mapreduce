package com.zlikun.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 12:30
 */
public class NovelMapperTest {

    @Test
    public void map() throws IOException {

        Text text = new Text("浩瀚的宇宙，无垠的星空，许多科学家推测，地球可能是唯一的生命源地。");
        MapDriver.newMapDriver(new NovelMapper())
                .withInput(new LongWritable(0), text)
                .withOutput(new Text("浩瀚"), new IntWritable(1))
                .withOutput(new Text("宇宙"), new IntWritable(1))
                .withOutput(new Text("无垠"), new IntWritable(1))
                .withOutput(new Text("星空"), new IntWritable(1))
                .withOutput(new Text("许多"), new IntWritable(1))
                .withOutput(new Text("科学"), new IntWritable(1))
                .withOutput(new Text("学家"), new IntWritable(1))
                .withOutput(new Text("科学家"), new IntWritable(1))
                .withOutput(new Text("推测"), new IntWritable(1))
                .withOutput(new Text("地球"), new IntWritable(1))
                .withOutput(new Text("可能"), new IntWritable(1))
                .withOutput(new Text("唯一"), new IntWritable(1))
                .withOutput(new Text("生命"), new IntWritable(1))
                .withOutput(new Text("源地"), new IntWritable(1))
                .runTest();

    }

}
