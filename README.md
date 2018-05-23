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


参考资料
- [推荐引擎初探](https://www.ibm.com/developerworks/cn/web/1103_zhaoct_recommstudy1/index.html#icomments)
- [开发者中心](dev.zhihuiya.com)
- [推荐系统_LFM和基于邻域(如UserCF、ItemCF)的方法的比较](https://blog.csdn.net/u011263983/article/details/51538971)
- [推荐系统_itemCF和userCF](https://blog.csdn.net/u011263983/article/details/51498458)
- [User-Based CF VS. Item-Based CF](https://my.oschina.net/zhangjiawen/blog/185625)
- [常用推荐系统算法总结](http://www.cnblogs.com/hdk1993/p/5114368.html)
- [协同过滤算法（UserCF + ItemCF）](https://www.jianshu.com/p/bf687ffc540d)
- https://github.com/ToryZhou/RecommendSystem
- https://github.com/xingzhexiaozhu/MovieRecommendation


推荐算法
- UserCF(User Based Collaborative Filtering)基于用户的协同过滤算法,推荐和当前用户相似度高的N个用户产生过行为的物品给当前用户；
- ItemCF(Item Based Collaborative Filtering)基于物品的协同过滤算法,推荐和当前用户历史上行为过的物品相似的物品给当前用户;
- LFM(latent factor model)隐语义模型,基于兴趣分类,对于某用户，先得到他的兴趣分类，再从分类中挑选他可能喜欢的物品;推荐解释ItemCF算法支持很好的推荐解释，它可以利用用户的历史行为解释推荐结果。但LFM无法提供这样的解释，它计算出的隐类虽然在语义上却是代表了一类兴趣和物品，却很难用自然语言描述并生成解释展示给用户。
- Mahout,Apache Mahout,包含许多实现，包括聚类、分类、推荐过滤、频繁子项挖掘;