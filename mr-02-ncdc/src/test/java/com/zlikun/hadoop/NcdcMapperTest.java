package com.zlikun.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 11:33
 */
public class NcdcMapperTest {

    @Test
    public void map() throws IOException {

        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" +
                "99999V0203201N00261220001CN9999999N9-00111+99999999999");
        MapDriver.newMapDriver(new NcdcMapper())
                .withInput(new LongWritable(0), value)
                .withOutput(new Text("1950"), new IntWritable(-11))
                .runTest();

    }

    @Test
    public void ignoreMissingTemperatureRecord() throws IOException {
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" +
                "99999V0203201N00261220001CN9999999N9+99991+99999999999");
        MapDriver.newMapDriver(new NcdcMapper())
                .withInput(new LongWritable(0), value)
                .runTest();
    }

}
