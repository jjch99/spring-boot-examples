# docker-example

### Docker Maven 插件

[spotify dockerfile-maven](https://github.com/spotify/dockerfile-maven)

[spotify docker-maven-plugin](https://github.com/spotify/docker-maven-plugin)

[fabric8io docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin)

本例中使用的`spotify dockerfile-maven`

```bash
# 构建
mvn clean package dockerfile:build

# 运行
docker run -p 8080:8081 spring-boot-example-docker:0.0.1-SNAPSHOT
```

### 参考
[docker-for-mac](https://docs.docker.com/docker-for-mac/)

[使用Spring Cloud与Docker实战微服务](https://eacdy.gitbooks.io/spring-cloud-book/content/)

