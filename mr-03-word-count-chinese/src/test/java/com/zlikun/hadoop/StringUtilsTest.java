package com.zlikun.hadoop;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 实际Hadoop-2.6不支持Lang3
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/7/27 14:05
 */
public class StringUtilsTest {

    @Test
    public void startsWithAny() {
        assertTrue(StringUtils.startsWithAny("#注释", "#", "%", "&"));
        assertTrue(StringUtils.startsWithAny("&音符", "#", "%", "&"));
        assertTrue(StringUtils.startsWithAny("%比例", "#", "%", "&"));
    }

    @Test
    public void isAlphanumeric() {
        // isAlphanumericSpace() 会误判中文
        assertTrue(StringUtils.isAlphanumeric("1000"));
        assertTrue(StringUtils.isAlphanumeric("green"));
        assertTrue(StringUtils.isAlphanumeric("abc100"));
        assertTrue(StringUtils.isAlphanumeric("神话"));

        // 所以 alpha 表示任意Unicode字符？！
        assertTrue(StringUtils.isAlpha("神话"));
    }

}
