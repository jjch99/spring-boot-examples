# spring-boot + dubbo + zipkin example

dubbo
> http://dubbo.apache.org/

dubbo-spring-boot-starter
> https://github.com/apache/incubator-dubbo-spring-boot-project/tree/0.1.x

dubbo-admin
> https://github.com/apache/incubator-dubbo-ops  
> 如果打包时提示 `maven-assembly-plugin` 有问题，可将其版本改为`2.4`试试  
> 最新的版本已经改成 Spring-Boot 程序，打的是`jar`包，启动时可通过 `java -Dkey=value ` 方式设置参数

brave-instrumentation-dubbo-rpc
> https://github.com/openzipkin/brave/tree/master/instrumentation/dubbo-rpc

other instrumentations
> https://github.com/openzipkin/brave/tree/master/instrumentation

zipkin-server 及相关说明
> https://github.com/openzipkin/zipkin/tree/master/zipkin-server

本示例中如果同时配置了`zipkin`地址和`kafka`地址，优先使用`kafka`收集器。
```
zipkin:
  tracing:
    endpoint: http://devhost:8411/api/v2/spans
    kafka:
      bootstrap-servers: devhost:8092
      topic: zipkin
```

ZipKin Kafka ES 的安装可参考相应子目录的说明。
