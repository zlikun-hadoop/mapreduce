package com.zlikun.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * 一些不相关的测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 12:21
 */
public class UncorrelatedTest {

    @Before
    public void init() {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
    }

    /**
     * 上传ZIP文件，并尝试读取内容
     *
     * @throws IOException
     */
    @Test
    public void hdfs() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        // 复制本地文件到HDFS
        Path file = new Path("mr/novel/input/zhe-tian.zip");
        if (!fs.exists(file)) {
            fs.copyFromLocalFile(new Path("D:\\Temporary\\data\\遮天.zip"), file);
        }
        // 验证HDFS中文件校验和
        assertEquals("MD5-of-0MD5-of-512CRC32C:ecc32bb990ad2c2f965fa5358ea7f982",
                fs.getFileChecksum(file).toString());

        // $ hdfs dfs -du -h mr/wc/
        // 7.1 M  7.1 M  mr/wc/zhe-tian.zip
        // 查看文件信息
        FileStatus status = fs.getFileStatus(file);
        // owner = hadoop, len = 7488517, replication = 1
        System.out.println(String.format("owner = %s, len = %d, replication = %d",
                status.getOwner(), status.getLen(), status.getReplication()));

        // 删除文件
        fs.deleteOnExit(file);
    }

    /**
     * 下载MR计算生成的文件
     */
    @Test @Ignore
    public void download() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        fs.copyToLocalFile(new Path("mr/novel/output/part-r-00000"),
                new Path("D:\\Temporary\\data\\results.txt"));
        fs.close();
    }

}
