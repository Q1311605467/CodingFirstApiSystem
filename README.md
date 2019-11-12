# 一码当先 Coding First 

## 介绍

一码当先 在线编程教育系统Web服务端（Spring boot重构版），
在SSM框架搭建的的FjutOnlineJudge 3.0版本上进行了重构，
并对一些不符合规范的代码和内容进行了修订

## 软件架构

1. 项目采用JAVA语言编写，版本为JDK1.8。
2. 项目采用Springboot + Mybatis框架，集成了Swagger,Druid等管理工具
3. 关系型数据库采用MySQL 8.0.15
4. 键值数据库采用Redis 3.2.100
5. Web客户端是采用Vue框架搭建的单页Web应用
6. 评测机是采用容器技术搭建的支持网络的独立应用

## 安装教程

1. 运行maven命令compiler
2. 在 [项目根目录]/target/文件夹下找到文件 [配置文件中的打包项目名].jar
3. 使用JDK1.8及以上版本的JAVA运行该jar，即可部署应用


## 使用说明

1. 下载并安装Idea，配置好开发环境，安装相关的插件
2. 下载并安装maven
3. 下载Spring-2.0.0.M5 SpringBoot工具
3. 下载并安装MySQL 8.0.15和MySQL可视化工具，项目运行时保持MySQL链接正常
4. 下载并安装Redis 3.2.100，项目运行时保持Redis Server在本地开启
5. 在application.yml，application-dev.yml文件中修改相关字段保证数据库链接正确
6. 运行 CodingFirstApplication.java
7. 在浏览器中输入地址 http://localhost:[配置文件中的端口]/[配置文件中的项目名]/swagger-ui.html
进入在线接口文档
8. 在浏览器中输入地址 http://localhost:[配置文件中的端口]/[配置文件中的项目名]/druid/index.html
，并输入用户名密码后，进入Druid管理界面

## 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request