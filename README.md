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
1. [数据展示功能](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/2fa84ef-17fc13385fb/2fa84ef-17fc13385fb.mp4?Expires=1648217481&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=H1PE%2FXS%2Fd%2Fcta%2BhLJvDD8rzHnb8%3D)
2. [讲师的后台管理](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/3e5b4b6e-17fc1385e70/3e5b4b6e-17fc1385e70.mp4?Expires=1648217830&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=uCeaOSrI%2BPq5K7OykHSTAUPXsYo%3D)
3. [课程的后台管理](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/34a3382b-17fc138b163/34a3382b-17fc138b163.mp4?Expires=1648217874&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=WbuEUXPKKwUdRLaP8vx%2FahtpdQk%3D)
4. [课程分类管理](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/d66d756-17fc138f5e8/d66d756-17fc138f5e8.mp4?Expires=1648217921&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=l1D47cG1XDmbOV1M7Erlxev5TXU%3D)
5. [教师展板功能](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/281546eb-17fc13b4cfa/281546eb-17fc13b4cfa.mp4?Expires=1648218098&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=XhSErmhCc0gWsbQKr6XK5KqvvUw%3D)
6. [课程详情页功能以及购买课程](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/8c83e02-17fc13b849c/8c83e02-17fc13b849c.mp4?Expires=1648218240&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=oO7eta%2BCA2jAyBDSGiv4fcu0KEA%3D)
7. [课程展示板块](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/45700f54-17fc13bbf71/45700f54-17fc13bbf71.mp4?Expires=1648218286&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=kH8MZVZpGOONx%2BltODU3OBFCWlE%3D)
8. [社区发表文章以及评论](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/2f267cc0-17fc13bf311/2f267cc0-17fc13bf311.mp4?Expires=1648218326&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=mnzjAVlDeGTMHIBu%2FVq7Uv7u%2F%2Fw%3D)
9. [社区文章删除和评论删除](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/31b72e0f-17fc13c3a19/31b72e0f-17fc13c3a19.mp4?Expires=1648218369&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=yVPVm02%2Fg5bZioLOV9WB3TI9gaw%3D)
10. [社区主页面功能展示](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/1f4f6025-17fc13ced9a/1f4f6025-17fc13ced9a.mp4?Expires=1648218402&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=UwNLsnjjiAbihMYt2ZABo3SH1zY%3D)
11. [注册功能](https://outin-a42fb0ba14f311ecb79500163e1c60dc.oss-cn-shanghai.aliyuncs.com/sv/3a5bda68-17fc13ced95/3a5bda68-17fc13ced95.mp4?Expires=1648218430&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=QlhO8CmyOwhmGS6f0duIkEnBKZI%3D)
