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
    - wechat
    - tf-idf 数据挖掘加权技术
- 前端
    - vue

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
- https://grouplens.org/datasets/movielens/

存在问题
- xiaoqin说：不能给竞争对手推荐新闻或专利，可以用算法服务已有的客户内部，使得搜索越搜越准确
    - 解决办法：我们可以是用基于专利的协同过滤（itemCF）,这个是根据个人的操作历史来进行推荐，而不涉及别人的操作历史。而且我们更多的是公平的针对个人。
    - 如果使用用户协同过滤或则隐语义，协议是没用的。我们关联的永远是个人与专利的关系。人与人的关系我们不需要存都是可以通过算法得出来的。
    - 还有可以用微信上收集的数据，服务我们2B产品的检索准确性。

- haibin说：客户没有那么多心思点击新闻详情页下面的专利列表，专业性太高的用户不点击，我们收集不到数据
    - 解决办法：根据搜索历史可以隔一段时间推荐一个能让他进入我们页面的链接，保持项目活跃度。
    - 如果新闻下面的5条专利太严肃，我们的Plan B,可以做一个下拉就出来360产品的咨讯(我们目前的360项目已经烂尾了，估计也是得不到数据了)，推荐算法可以采用推荐专利一样的算法。
    - 主要是吸引用户点击，方便收集数据。
    - 手机端的搜索相比于电脑端，**更加简单快捷无脑使用，并不需要登录**。随时随地想到就可以用一下。

- chaoge（女，analytics产品经理）说： 用户不太愿意在手机上查看专利信息，如果用户不开始使用，后面的收集数据也不能进行
    - 解决办法： 我们并不要求所有人都用手机端看，只要有人或则少部分人使用，我们都可以提供这样的功能

- dandan说：这个idea不错，可以用于服务已有客户
    - 解决办法：好评无解，可以把好评的说法放到我们的产品亮点

- shupan说：我们能收集到用户画像，这样我们就可以做很多事情
    - 解决办法：好评无解，可以把好评的说法放到我们的产品亮点

- Oscar说：在搜索了诺基亚过后，推送消息应该最先看到的是诺基亚消息。 这个是由于我们的演示过程没有先推送诺基亚，再推送索尼，而导致的误解。
    - 解决：演示尽量简单，采用容易理解的方式。oscar 说的重点，**我们是推荐引擎，推荐一定要准确**。我们重点要突出。

新增功能点
1. 简单搜索结果的标题增加链接到详情
2. 简单搜索结果后面新增 查看更多 （查看更多列表页能先做静态，有时间可以实现动态）
3. 专利详情增加 收藏和取消收藏 （收藏按钮有时间做就动态，没时间就静态;收藏列表页先做静态，有时间做动态）