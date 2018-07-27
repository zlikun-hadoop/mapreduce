package com.zlikun.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 使用中文分词，统计《遮天》词频
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 13:11
 */
public class NovelMapReduce extends Configured implements Tool {

    public static void main(String[] args) throws Exception {

        // 仅供测试(假定没通过命令行传参数代理是在Windows下执行，调试)
        if (args.length < 2) {
            args = new String[]{
                    "mr/novel/input",
                    "mr/novel/output"
            };
            System.setProperty("HADOOP_USER_NAME", "hadoop");
        }
        int exitCode = ToolRunner.run(new NovelMapReduce(), args);
        System.exit(exitCode);

    }

    @Override
    public int run(String[] args) throws Exception {
        // 检查命令行参数
        if (args == null || args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output>\n", this.getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        // 仅供调试使用，准备输入、输出目录等
        prepare(this.getConf());

        // 配置任务
        Job job = Job.getInstance(getConf());
        job.setJar(Paths.get("mr-03-word-count-chinese/target/mr-word-count-chinese.jar").toAbsolutePath().toString());
        job.setJarByClass(this.getClass());

        // 添加依赖库(HDFS)
        job.addArchiveToClassPath(new Path("lib/com/huaban/jieba-analysis/1.0.2/jieba-analysis-1.0.2.jar"));

        // 配置输入、输出文件路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 设置Mapper、Combiner、Reducer
        job.setMapperClass(NovelMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);

        // 设置输出键、值类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 等待任务运行完成
        return job.waitForCompletion(true) ? 0 : 1;
    }

    /**
     * 为了测试准备一些数据
     */
    private void prepare(Configuration conf) throws IOException {

        FileSystem fs = FileSystem.get(conf);

        // 检查output目录是否存在，存在则删除，目录为：用户目录(hadoop)下的mr/output目录
        Path output = new Path("mr/novel/output");
        if (fs.exists(output)) {
            // 使用递归删除
            if (!fs.delete(output, true)) {
                System.out.println("删除输出目录失败!");
            }
        }

        // 检查input目录是否存在，不存在则创建
        Path input = new Path("mr/novel/input");
        if (!fs.exists(input)) {
            // 创建目录及权限，依次：user、group、other
            fs.mkdirs(input, new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.READ));
        }

        // 上传小说数据文件
        Path file1 = new Path("mr/novel/input/zhe-tian.txt");
        if (!fs.exists(file1)) {
            fs.copyFromLocalFile(new Path("D:\\Temporary\\data\\遮天.txt"), file1);
        }

        // 上传分词JAR
        Path file2 = new Path("lib/com/huaban/jieba-analysis/1.0.2/jieba-analysis-1.0.2.jar");
        if (!fs.exists(file2)) {
            fs.copyFromLocalFile(new Path("D:\\Portable\\m2repos\\com\\huaban\\jieba-analysis\\1.0.2\\jieba-analysis-1.0.2.jar"), file2);
        }

    }

}
