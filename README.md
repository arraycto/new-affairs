# new-affairs

#### 介绍
模拟高并发场景下的在线选课系统（后端）

截止日前，实现了基本的用户管理，在学生端实现了课程查询、选课及退课，教师端实现了课程发布及课程管理等功能

在实现基本功能的基础上，对核心模块在高并发场景下做了一定的优化

访问前端：[new-affairs-ui](https://gitee.com/Surliness/new-affairs-ui)

#### 软件架构
使用当前主流的前后端分离模式进行开发

前端 Vue + ElementUI

后端 Spring Boot + Spring Cloud Alibaba + MyBatis-Plus + RabbitMQ + Redis + MySQL

Web服务器 Nginx

#### 核心模块

在高并发场景下采用了一定的优化手段最大程度保证了服务的可靠性

* 服务单一职责、独立部署
* 链接加密
* 数据预热、快速处理
* 动静分离
* 恶意请求拦截
* 流量错峰
* 限流、熔断、降级
* 队列削峰

#### 运行说明

前端：

1.  克隆代码到本地
2.  将其导入至你所熟悉的集成开发环境中
3.  使用`npm install`安装对应依赖
4.  根据个人情况修改`/config/index.js`中的配置信息
5.  使用`npm run dev`启动项目

后端：

1. 克隆代码到本地
2. 将其导入至你所熟悉的集成开发环境中
3. 安装对应依赖并配置数据源及消息队列等
4. 启动项目

如启动后无法正常访问后台接口，可采用以下解决方案

方案1：

* 在前端项目中修改请求的URL地址前缀为后端启动的网关接口

  例：`http://127.0.0.1:88/api/student/student/login`

方案2：

* 使用Nginx将前后端配置在同一域名下

  ```shell
  listen       80;
  server_name  demo.com;
  
  
  location / {
      proxy_set_header Host $host;
      proxy_pass http://127.0.0.1:8080;
  }
  
  location /api/ {
      proxy_set_header Host $host;
      proxy_pass http://upsteam;
  }
  
  ```

  以上为基本配置，更多详情请参考[Nginx](https://nginx.org/en/)