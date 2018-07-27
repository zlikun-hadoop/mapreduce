## 使用中文分词，统计《遮天》词频
> 过程中遇到文件编码问题，原来文件是GBK编码，统计结果发现单词是乱码，后使用SublimeText3转换为UTF-8编码后正常了  
> 另外不知道为什么压缩成zip文件后，无论如何都会乱码，暂不理会 。。。  
> 分词不太理想，应该需要自定义字典，不过工作量太大了

#### 依赖文件及库
```
$ hdfs dfs -ls lib/com/huaban/jieba-analysis/1.0.2
Found 1 items
-rw-r--r--   1 hadoop supergroup    2189961 2018-07-27 01:21 lib/com/huaban/jieba-analysis/1.0.2/jieba-analysis-1.0.2.jar

$ hdfs dfs -ls mr/novel/input
Found 1 items
-rw-r--r--   1 hadoop supergroup    7488517 2018-07-27 01:16 mr/novel/input/zhe-tian.zip
```

#### 查看MR程序计算结果
```
$ hdfs dfs -ls mr/novel/output   
Found 2 items
-rw-r--r--   1 hadoop supergroup          0 2018-07-27 01:22 mr/novel/output/_SUCCESS
-rw-r--r--   1 hadoop supergroup     349779 2018-07-27 01:22 mr/novel/output/part-r-00000
$ hdfs dfs -du -h mr/novel/output
0        0        mr/novel/output/_SUCCESS
341.6 K  341.6 K  mr/novel/output/part-r-00000
```