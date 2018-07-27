package com.zlikun.hadoop;

import org.junit.Test;

import java.nio.file.Paths;

/**
 * 一些不相关的测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 9:25
 */
public class UncorrelatedTest {

    @Test
    public void path() {
        System.out.println(Paths.get("target/").toAbsolutePath().toString());
    }

}
