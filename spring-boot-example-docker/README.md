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
[Docker — 从入门到实践](https://yeasy.gitbooks.io/docker_practice/)

[docker-for-mac](https://docs.docker.com/docker-for-mac/)

[使用Spring Cloud与Docker实战微服务](https://eacdy.gitbooks.io/spring-cloud-book/content/)

[k8s-docker-for-mac](https://github.com/maguowei/k8s-docker-for-mac)

[Docker 用户使用 kubectl 命令指南](https://kubernetes.io/zh/docs/user-guide/docker-cli-to-kubectl/)
