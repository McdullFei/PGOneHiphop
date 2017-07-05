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





## TODO 由于不是web项目缺少web框架
- 请求报文加密解密拦截器
- url防盗链处理(url sign加密token,url设置有效时间)
- 用户session处理,暂不使用第三方框架,重写一个类似acctk校验逻辑代码(user access token)
- 运营后台权限角色统一架构(考虑第三方)

## 6.5号更新
- 添加swagger2
- 增加controller demo
- 增加junit demo
- 拆分module:business只放业务代码,公共工具类放在common-utils中实现解耦,暂时这么分

## 6.6号更新
- 重新整理module并迁移代码
- 设计session逻辑:提供注解sessionLoader在登录成功是主动记录session,通过json的序列化反序列化实现存储在redis中的json和userbean映射
提供拦截器获取登录用户的session获取userbean放入tread localcache

## 6.8号更新
- 增加HttpClinet和HttpAsyncClient来解决在RestTemplate处理不了的情景
- 重新规划module pom,整理依赖包(common-utils纯java项目不与spring框架关联,framework是一个spring项目不是boot项目,可以使用spring对公共类封装(依赖IOC容器))
- TODO business 配置文件区分

## 6.12号更新
- 增加redis massagemack序列化方式


## 6.21号更新
- 增加UserSessionManager,主要使用了spring 的cachemanager来支持多种缓存服务器,不强行绑定某种缓存服务器
- aop来支持登陆用户session缓存,业务层实现Login接口

## 6.22号更新
- 增加url防盗链拦截器VerifySignInterceptor,是否选择使用在business层进行配置(xml,或者Bean注解)
- 注意:所有工具类都必须优先考虑spring\Apachecommon\guava中是否有,避免重复造轮子,比如加密算法优先考虑Apache common的DigestUtils类




## TODO sso,统一认证系统;oauth
- 使用jwt代替https进行sso认证(反向代理服务无法缓存https,证书也难以维护)
    - jwt经常用于设计用户认证和授权系统(登陆\api)，甚至实现Web应用的单点登录。
    - oauth的实现
    - jjwt和auth0-jwt
    - http://blog.leapoahead.com/2015/09/06/understanding-jwt/ 
    

