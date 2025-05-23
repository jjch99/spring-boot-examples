# grpc example
 
gRPC简单示例，集成`zipkin`作为调用链跟踪。

启动`zipkin`服务
```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

official grpc-java
> https://github.com/grpc/grpc-java

gRPC 官方文档中文版 V1.0
> https://doc.oschina.net/grpc

awesome-grpc
> https://github.com/grpc-ecosystem/awesome-grpc

grpc-spring-boot-starter
>
> https://github.com/LogNet/grpc-spring-boot-starter
>
> https://github.com/yidongnan/grpc-spring-boot-starter
>
> https://github.com/saturnism/spring-boot-starter-grpc
>
> https://github.com/ChinaSilence/spring-boot-starter-grpc

grpc-gateway
> https://github.com/grpc-ecosystem/grpc-gateway

grpc examples
> https://github.com/saturnism/grpc-java-by-example
>
> https://github.com/WThamira/gRpc-spring-boot-example

grpc优点
- 跨语言支持
- 性能好

grpc开发的一些问题
- 生成的代码不符合规范，如果代码库有自动的规范检查，这里可能会有些麻烦，需要有排除机制。
- 对Java不够友好，生成的代码没有可读性，用起来也是有点奇奇怪怪（相对于dubbo等基于Java设计的RPC框架）。
- 先写proto文件再生成代码，稍显繁琐。
- 对象属性赋值(set方法)不能赋null，会报NPE，每个set前要if一下，太麻烦。
- 如果涉及从其他RPC框架迁移，成本有点高。
