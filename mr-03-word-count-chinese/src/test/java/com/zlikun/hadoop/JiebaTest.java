package com.zlikun.hadoop;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://github.com/huaban/jieba-analysis
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 12:43
 */
public class JiebaTest {

    @Test
    public void test() {
        // 载入自定义字典，没有提供单个添加的API
        // WordDictionary.getInstance().loadUserDict(Paths.get(""), StandardCharsets.UTF_8);
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String sentence = "扁担长，板凳宽，板凳没有扁担长，扁担没有板凳宽。扁担要绑在板凳上，板凳不让扁担绑在板凳上，扁担偏要扁担绑在板凳上。";
        List<SegToken> tokens = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
        Set<String> ignoreWords = Stream.of("，", "。").collect(Collectors.toSet());
        for (SegToken token : tokens) {
            // 忽略字符
            if (ignoreWords.contains(token.word)) continue;
            // "[" + this.word + ", " + this.startOffset + ", " + this.endOffset + "]";
            System.out.println(token);
        }
    }

    @Test
    @Ignore
    public void analysis_txt_file() throws IOException {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        // 读取小说文件，进行中文分词
        for (String line : Files.readAllLines(Paths.get("D:\\Temporary\\data\\遮天.txt"), StandardCharsets.UTF_8)) {
            List<SegToken> tokens = segmenter.process(line, JiebaSegmenter.SegMode.INDEX);
            System.out.println(tokens);
        }
    }

}

/* ------------------------------------------------
[扁担, 0, 2]
[长, 2, 3]
[板凳, 4, 6]
[宽, 6, 7]
[板凳, 8, 10]
[没有, 10, 12]
[扁担, 12, 14]
[长, 14, 15]
[扁担, 16, 18]
[没有, 18, 20]
[板凳, 20, 22]
[宽, 22, 23]
[扁担, 24, 26]
[要, 26, 27]
[绑, 27, 28]
[在, 28, 29]
[板凳, 29, 31]
[上, 31, 32]
[板凳, 33, 35]
[不让, 35, 37]
[扁担, 37, 39]
[绑, 39, 40]
[在, 40, 41]
[板凳, 41, 43]
[上, 43, 44]
[扁担, 45, 47]
[偏要, 47, 49]
[扁担, 49, 51]
[绑, 51, 52]
[在, 52, 53]
[板凳, 53, 55]
[上, 55, 56]
------------------------------------------------ */