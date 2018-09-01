# grpc example
 
gRPC简单示例，集成`zipkin`作为调用链跟踪。


启动`zipkin`服务
```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

awesome-grpc
> https://github.com/grpc-ecosystem/awesome-grpc

grpc-spring-boot-starter
>
> https://github.com/LogNet/grpc-spring-boot-starter
>
> https://github.com/yidongnan/grpc-spring-boot-starter
>
> https://github.com/saturnism/spring-boot-starter-grpc

grpc优点
- 跨语言支持
- 性能好

grpc开发的一些问题（以下这些问题足以让人放弃使用）
- 生成的代码不符合规范，如果代码库有自动的规范检查，这里可能会有些麻烦。
- 对Java不够友好，生成的代码没有可读性，即使是用起来也是有点奇奇怪怪（相对于dubbo等基于Java设计的RPC框架）。
- 先写proto文件再生成代码，稍显繁琐。
- 对象属性赋值(set方法)不能赋null，会报NPE，每个set前要if一下，吃相太难看。
- 生态不成熟，很多东西需要开发、封装。
- 如果涉及从其他RPC框架迁移，成本有点高。
