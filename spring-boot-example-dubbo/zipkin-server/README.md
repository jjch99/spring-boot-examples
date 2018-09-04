## ZipKin + Kafka-SSL + Elasticsearch

### 下载ZipKin

```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
```

### 配置
将`log4j2.xml` `zipkin.conf` `zipkin.sh` 等文件拷贝到与`zipkin.jar`相同目录，编辑`zipkin.conf`，根据情况配置Kafka以及ES相关参数。

### 运行
启动
```
sh zipkin.sh start
```

停止
```
sh zipkin.sh stop
```
