# WTookits

**Introduction:**
```shell
  在文本分析研究中，不管是文本检索、文本分类还是文本聚类问题，首先要做的都是对文本进行分词，然后计算每一个词的权重，然后再去做后续的工作。词语权重计算是一个前置工作。同时，对于不同的文本分析问题，我们所采用的权重计算方法可以是相同的，也可以是不同的。比如，我们在对商品评论进行分类的时候，我们可能会用TF权重计算方法，也可能会用到IDF权重计算方法，或者是TF-IDF方法，当我们对新闻进行聚类的时候，我们也可能用到TF/IDF/TF-IDF中的一个方法或多个方法。也就是说，词的权重计算方法是与特定问题无关的，可以抽取出来独立实现的部分。因此，基于代码复用的思想，我们的项目就是实现一个计算词的权重的工具包，提供多种词的权重计算方法供用户选择，为用户减少不必要的工作。
  项目名称：WTookits
  项目描述：WTookits，即Weight Tookits（权重工具）。用于对字符序列进行分词并对分词进行加权，以供进一步的自然语言处理。
```

**simple example：**

**1，首先进行配置configuration.properties文件**
```shell
TermWeighting = TF   #使用的weighting算法
DirPath = yourPath/data  #输入需要weighting文件夹路径
DestPath = yourpath/save.txt   #输出文件的路径
```
**2,然后编译完项目，就可以直接运行**
```shell
Main.class yourPath/configuration.properties
```
