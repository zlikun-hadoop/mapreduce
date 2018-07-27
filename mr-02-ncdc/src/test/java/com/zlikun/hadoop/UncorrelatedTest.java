package com.zlikun.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * 一些不相关的测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 9:25
 */
public class UncorrelatedTest {

    /**
     * 可以在HDFS上通过命令行验证：
     * $ hdfs dfs -ls mr/ncdc/1901.gz
     * -rw-r--r--   1 hadoop supergroup      73867 2018-07-27 00:02 mr/ncdc/1901.gz
     * $ hdfs dfs -du -h mr/ncdc/1901.gz
     * 72.1 K  72.1 K  mr/ncdc/1901.gz
     * $ hdfs dfs -checksum mr/ncdc/1901.gz
     * mr/ncdc/1901.gz MD5-of-0MD5-of-512CRC32C        000002000000000000000000d44158cdb9b6ef9d7755b889ce5028c9
     * @throws IOException
     */
    @Test
    public void copyFromLocalFile() throws IOException {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        // 复制本地文件到HDFS
        Path file = new Path("mr/ncdc/1901.gz");
        if (!fs.exists(file)) {
            fs.copyFromLocalFile(new Path("data/1901.gz"), file);
        }
        // 验证HDFS中文件校验和
        assertEquals("MD5-of-0MD5-of-512CRC32C:d44158cdb9b6ef9d7755b889ce5028c9", fs.getFileChecksum(file).toString());
    }

}
