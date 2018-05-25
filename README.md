# news
A recommend system, using sprint boot+mysql+jpa+wechat+patent api

初始化数据库
```
mysql -uroot -proot
CREATE DATABASE news DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```
项目使用说明

    在resources/api目录下面有 rest的模拟请求，点击行号旁边的运行三角形按钮运行
    需要在idea安装lombok插件
    wechat | patent api调用的key在profile文件application-dev.properties 未上传到github

推荐算法
- UserCF(User Based Collaborative Filtering)基于用户的协同过滤算法,推荐和当前用户相似度高的N个用户产生过行为的物品给当前用户；
- ItemCF(Item Based Collaborative Filtering)基于物品的协同过滤算法,推荐和当前用户历史上行为过的物品相似的物品给当前用户;
- LFM(latent factor model)隐语义模型,基于兴趣分类,对于某用户，先得到他的兴趣分类，再从分类中挑选他可能喜欢的物品;推荐解释ItemCF算法支持很好的推荐解释，它可以利用用户的历史行为解释推荐结果。但LFM无法提供这样的解释，它计算出的隐类虽然在语义上却是代表了一类兴趣和物品，却很难用自然语言描述并生成解释展示给用户。
- Mahout,Apache Mahout,包含许多实现，包括聚类、分类、推荐过滤、频繁子项挖掘;

技术点
- 后端
    - 推荐引擎，实现了基于用户协同过滤UserCF,基于物品协同过滤ItemCF,隐语义模型LFM
    - 通过Open Api 调用专利搜索，详情，价值等等五个API
    - 调用 R&D API, 中文新闻文章的实体识别API, 对用户搜索的word或者phrase找出和它们相似的word或者phras, 将中文公司名分解成其前缀与后缀, 专有名词缩写匹配等4个api
- 前端
    - a

新闻推荐流程：
- 使用tf-idf获取新闻关键词，加上open-api的关键信息提取，如申请组织，申请人和位置信息获取相关专利，放在新闻后面
- 微信中的用户搜索记录会被记录，然后通过全模式分词及open-api将关键词及其近义词记录在solr中
- 新闻在记录入库时，通过之前获取的关键词及相关信息与用户搜索记录进行比对，将新闻推送给感兴趣的用户

参考资料
- [推荐引擎初探](https://www.ibm.com/developerworks/cn/web/1103_zhaoct_recommstudy1/index.html#icomments)
- [开发者中心](dev.zhihuiya.com)
- [推荐系统_LFM和基于邻域(如UserCF、ItemCF)的方法的比较](https://blog.csdn.net/u011263983/article/details/51538971)
- [推荐系统_itemCF和userCF](https://blog.csdn.net/u011263983/article/details/51498458)
- [User-Based CF VS. Item-Based CF](https://my.oschina.net/zhangjiawen/blog/185625)
- [常用推荐系统算法总结](http://www.cnblogs.com/hdk1993/p/5114368.html)
- [协同过滤算法（UserCF + ItemCF）](https://www.jianshu.com/p/bf687ffc540d)
- [Mahout介绍和简单应用](https://www.cnblogs.com/ahu-lichang/p/7073836.html) 用于该项目
- [准确率(Accuracy), 精确率(Precision), 召回率(Recall)和F1-Measure](https://blog.argcv.com/articles/1036.c)
- https://github.com/ToryZhou/RecommendSystem 用于该项目
- https://github.com/xingzhexiaozhu/MovieRecommendation