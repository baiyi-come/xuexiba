# 学习吧后端代码 
## 关键技术：SpringBoot, MyBatis-Plus, SpringCloud，
  ## 数据缓存：Redis 
  ## 注册中心：Nacos 
  ## 数据库：MySql
  ## JDK:JDK1.8 
## 本项目后端有三大模块： 
  1. common: 通用工具类 
  2. infrastructure：网关 
  3. service: 提供各种服务支持
  
### infrastructure下有：
  #### api_gateway: 网关模块，所有请求经此转发给相应的模块进行数据处理

### service下有： 
    1. service-community: 社区模块，提供文章发表和交流等功能 
    2. service-lar: 登录注册模块 
    3. service-order：提供支付服务 
    4. service-sms: 提供短信验证服务 
    5. service-oss: 提供对象存储服务 
    6. service-statistics: 提供日常信息统计服务（日活，注册量，播放量，课程上新数） 
    7. service-vod: 提供视频上传，删除等功能
    
   
## 功能演示
1. [数据展示功能](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/2fa84ef-17fc13385fb/2fa84ef-17fc13385fb.mp4?Expires=1649162034&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=HZ2OxgkstPxCWHUFv75xzn2RBj4%3D)
2. [讲师的后台管理](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/3e5b4b6e-17fc1385e70/3e5b4b6e-17fc1385e70.mp4?Expires=1649162173&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=U21UyTKQ%2B4Lu5TFft1qMUA0%2F87c%3D)
3. [课程的后台管理](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/34a3382b-17fc138b163/34a3382b-17fc138b163.mp4?Expires=1649162206&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=3nN%2BbDHl6aNLBenRKKLcGTsB9CQ%3D)
4. [课程分类管理](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/d66d756-17fc138f5e8/d66d756-17fc138f5e8.mp4?Expires=1649162237&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=VWykuj9siqIGd1rlXceP1FKQRpA%3D)
5. [教师展板功能](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/281546eb-17fc13b4cfa/281546eb-17fc13b4cfa.mp4?Expires=1649162281&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=deH2W0x2nc930GySy%2BgvbKPBkEs%3D)
6. [课程详情页功能以及购买课程](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/8c83e02-17fc13b849c/8c83e02-17fc13b849c.mp4?Expires=1649162303&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=COTYXEvO9gKGLVtRa3oVvGJgF%2Fg%3D)
7. [课程展示板块](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/45700f54-17fc13bbf71/45700f54-17fc13bbf71.mp4?Expires=1649162325&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=8318iyOcU3VJYuy9PhcFYJVH5CU%3D)
8. [社区发表文章以及评论](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/2f267cc0-17fc13bf311/2f267cc0-17fc13bf311.mp4?Expires=1649162347&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=kQwlYQ5e%2BO2nVjD9BpFRjuU%2Fwy8%3D)
9. [社区文章删除和评论删除](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/31b72e0f-17fc13c3a19/31b72e0f-17fc13c3a19.mp4?Expires=1649162369&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=4xr8VkxGntS%2FMnARbS%2BWPGTsStE%3D)
10. [社区主页面功能展示](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/1f4f6025-17fc13ced9a/1f4f6025-17fc13ced9a.mp4?Expires=1649162422&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=WukGVfkuNND3KzJ0Um%2F63jAnCo4%3D)
11. [注册功能](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/3a5bda68-17fc13ced95/3a5bda68-17fc13ced95.mp4?Expires=1649162388&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=nOipWF%2FnV2NpKlKAVEtmC1jz%2Bts%3D)
