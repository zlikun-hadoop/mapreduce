package com.zlikun.hadoop;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 12:32
 */
public class NovelMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private IntWritable one = new IntWritable(1);
    private Text word = new Text();

    private JiebaSegmenter segmenter = new JiebaSegmenter();


    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        // 加载自定义字典，需要解决文件路径问题(否则影响单元测试)
        // WordDictionary.getInstance().loadUserDict(, StandardCharsets.UTF_8);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        List<SegToken> tokens = segmenter.process(value.toString(), JiebaSegmenter.SegMode.INDEX);
        if (CollectionUtils.isEmpty(tokens)) return;

        for (SegToken token : tokens) {
            // 忽略单字符
            if (StringUtils.isBlank(token.word) || token.word.length() <= 1) continue;
            // 忽略指定前缀的词
            if (StringUtils.startsWithAny(token.word.toLowerCase(), new String[]{"-", "#", "&", ".", "_", "一"}))
                continue;
            // 忽略纯字母、数字和空格
            if (token.word.matches("\\w+")) continue;

            word.set(token.word);
            context.write(word, one);
        }

    }

}
