## 1.1.0: 2019-5-23
* 增加服务端节点操作，提供listGroups/listStorage/deleteStorage方法

## 1.0.1: 2019-5-21
* 增加CommandStatusException的日志输出

## 1.0.0: 2019-5-17
* 基于官方版本重写了Fastdfs的Java Client
* 此版本采用Spring Boot开发框架，并且可自动装配Tracker连接
* 使用apache common pool2重写了集群环境下的Tracker连接池
* 初始版本提供了基本的操作命令upload/download/getMeta/delete
* Unit Test需要本地Fastdfs环境的支持，可使用官方Dockerfile进行编译和部署

