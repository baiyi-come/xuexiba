学习吧后端代码 
关键技术：SpringBoot, MyBatis-Plus, SpringCloud，Redis 
  注册中心：Nacos 
  数据库：MySql J
  DK:JDK1.8 
本项目后端有三大模块： 
  common: 通用工具类 
  infrastructure：网关 
  service: 提供各种服务支持
  
infrastructure下有：
  api_gateway: 网关模块，所有请求经此转发给相应的模块进行数据处理

service下有： 
    service-community: 社区模块，提供文章发表和交流等功能 
    service-lar: 登录注册模块 
    service-order：提供支付服务 
    service-sms: 提供短信验证服务 
    service-oss: 提供对象存储服务 
    service-statistics: 提供日常信息统计服务（日活，注册量，播放量，课程上新数） 
    service-vod: 提供视频上传，删除等功能
