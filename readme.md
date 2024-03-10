## Java基础框架
[![Java CI](https://github.com/mybatis/spring/actions/workflows/ci.yaml/badge.svg)](https://github.com/mybatis/spring/actions/workflows/ci.yaml)
[![Coverage Status](https://coveralls.io/repos/mybatis/spring/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/spring?branch=master)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis-spring/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis-spring)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/https/oss.sonatype.org/org.mybatis/mybatis-spring.svg)](https://oss.sonatype.org/content/repositories/snapshots/org/mybatis/mybatis-spring/)
[![License](https://img.shields.io/:license-apache-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
```markdown
    Java基础框架主要提供jar版本统一管理，以及通用工具包，方便快速开发业务
    giimall-common-core: 主要提供一些公共组件的扩展，比如雪花算法id生成器、redis客户端默认设置protobuf序列化
    giimall-common-model: 主要提供一些公用的对象，比如标准响应体、常量、公共异常类
    giimall-common-util: 主要是提供一些通用的工具类
    giimall-dubbo-spring-boot-starter: 集成这个jar就会自动集成dubbon远程调用相关的功能，该包聚合官方dubbo相关组件外，还针对我们基础框架做了一些定制化内容
    giimall-generator: 代码生产器，主要根据业务表生成标准的增删改查接口
    giimall-mybatis-spring-boot-starter: 集成这个jar就会自动集成mybatis-pluse相关的功能，该包除了聚合mybatis-pluse相关的功能外，还针对业务需要做了一定的扩展
    giimall-rpc-spring-boot-starter: 提供JsonRpc远程调用PHP服务功能
    giimall-url-regist-spring-boot-starter: 集成该jar就会提供接口自动注册到权限表功能

```

