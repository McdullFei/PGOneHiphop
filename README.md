atlas_base
==========

atlas服务端基础框架

## 框架基于spring-boot
- jdbc
- redis
- mongo
- logback
- guava
- mybatis
- P6Spy
- java 8
- spring oauth2 + jwt

## 结构说明
- admin 运营后台(spring-boot)
- business 业务接口(web\app) spring-boot项目
    - 全局Id生成器:IdGenerator,依赖mongo\redis
    - http接口调用:RestOperations
    - 持久层访问:mybatis
    - 分布式缓存:redistemplate(封装一层统一key,value的序列化反序列化)
    - nosql:mongodb
    - 本地缓存统一:guava cache,禁用全局static map结构
    - 类似collection判断是否为null,string是否为空统一,random等
    优先使用Apache common.lang3或者spring common或者guava,禁止重复早轮子
- common_utils 工具类,不依赖框架
- framework 封装的框架级代码,比如session等,仅依赖spring
- 整套框架是以spring为驱动的
- 依赖关系:
![依赖关系](seq-queue.png) 







## 20170810更新
- 增加url防盗链
    - VerifySignInterceptor 实现类
    - 通过路径和访问参数的hash和url有效时间设置来保证防盗链
    - 单元测试需要带sign和t
- 增加返回参数的json封装
- 增加统一异常处理


## 20170920更新
- 增加springboot oauth2.0 demo，使用授权码方式整合jwt
- 在单独的oauth2 模块中，下面是三个独立spring boot项目，单独启动即可
- ps: oauth2.0授权服务的session保存client端，这样需要客户端自己实现登陆。这里也可以使用auth端的登陆，这样需要auth端集成session机制（TODO spring session）
- 参考
   http://blog.leapoahead.com/2015/09/06/understanding-jwt/ 
   https://lidong1665.github.io/2017/03/14/Spring-Security-OAuth2-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97/

## 20170926更新
- oauth2服务jwt实现非对称加密
- oauth2服务jwt协议报文扩充，增加busiId字段（业务需求往往需要扩充jwt报文的情况）

> oauth2 token 非对称加密执行过程

- 生成rsa证书

```java
keytool -genkeypair -alias atlas -keyalg RSA -keypass atlas123 -keystore atlas.jks -storepass atlas123
```

- 通过证书到处秘钥

```java
keytool -list -rfc --keystore atlas.jks | openssl x509 -inform pem -pubkey
```


## TODO 集成springcloud


