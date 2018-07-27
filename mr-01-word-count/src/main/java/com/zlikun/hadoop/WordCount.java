package com.zlikun.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/26 22:24
 */
public class WordCount {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 仅供测试(假定没通过命令行传参数代理是在Windows下执行，调试)
        if (args.length < 2) {
            args = new String[]{
                    "mr/input",
                    "mr/output"
            };
            System.setProperty("HADOOP_USER_NAME", "hadoop");
        }


        Configuration conf = new Configuration();

        // 准备数据
        prepare(conf);

        // 配置MapReduce并等待任务运行结束
        Job job = Job.getInstance(conf);
        // 指定运行的JAR路径
        job.setJar(Paths.get("mr-01-word-count/target/mr-word-count.jar").toAbsolutePath().toString());
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

    /**
     * 为了测试准备一些数据
     */
    private static void prepare(Configuration conf) throws IOException {

        FileSystem fs = FileSystem.get(conf);

        // 检查output目录是否存在，存在则删除，目录为：用户目录(hadoop)下的mr/output目录
        Path output = new Path("mr/output");
        if (fs.exists(output)) {
            // 使用递归删除
            if (!fs.delete(output, true)) {
                System.out.println("删除输出目录失败!");
            }
        }

        // 检查input目录是否存在，不存在则创建
        Path input = new Path("mr/input");
        if (!fs.exists(input)) {
            // 创建目录及权限，依次：user、group、other
            fs.mkdirs(input, new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.READ));
        }

        // 检查测试文件是否存在，不存在则创建
        Path file = new Path("mr/input/servers.txt");
        if (!fs.exists(file)) {
            final String text = "apache nginx tomcat nginx jetty apache nginx";
            InputStream in = new ByteArrayInputStream(text.getBytes());
            FSDataOutputStream out = fs.create(file, true);
            IOUtils.copyBytes(in, out, 4096, true);
        }

    }

}
