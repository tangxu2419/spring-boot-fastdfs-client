
# FastDFS spring-boot-fastdfs-client SDK

## 运行环境要求
目前客户端主要依赖于SpringBoot

* JDK环境要求  11

## spring-boot-fastdfs-client 使用方式
Maven依赖为
```xml
<dependency>
    <groupId>com.vcredit.framework</groupId>
    <artifactId>spring-boot-fastdfs-client</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```
## yml 配置文件

```
fastdfs:
  upload-fail-ignore: true
  connectTimeout: PT5S
  soTimeout: PT15S
  charset: UTF-8
  pool:
    max-total: 50
    test-while-idle: true
    block-when-exhausted: true
    max-wait-millis: PT2S
    min-evictable-idle-time-millis: PT180S
    time-between-eviction-runs-millis: PT60S
    num-tests-per-eviction-run: -1
  cluster:
    nodes:
      - 10.138.30.40:22122
```

    注1：fastdfs.cluster.nodes指向您自己IP地址和端口，1-n个
    注2：除了fastdfs.cluster.nodes，其它配置项都是可选的

## 使用接口服务对Fdfs服务端进行操作

主要接口

FastdfsClient-