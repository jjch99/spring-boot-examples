## Kafka配置SSL

### 基本安装
略。

### 生成密钥和证书
可使用`gen-key-and-cert.sh`脚本来生成所需的密钥和证书，将生成的`jks`文件放到`${KAFKA_HOME}/keys`目录。

### Server端配置
在`config/server.properties`文件中增加SSL相关配置：
```
ssl.keystore.location=keys/kafka-server-ks.jks
ssl.keystore.password=server1234567
ssl.key.password=server1234567
ssl.truststore.location=keys/kafka-server-ts.jks
ssl.truststore.password=server1234567
ssl.client.auth=required
listeners=PLAINTEXT://:8092,SSL://:8093
```

### 验证
新建文件 `config/client-ssl.properties`
```
security.protocol=SSL
ssl.truststore.location=keys/kafka-client-ts.jks
ssl.truststore.password=client1234567
ssl.keystore.location=keys/kafka-client-ks.jks
ssl.keystore.password=client1234567
ssl.key.password=client1234567
```

启动命令行消费者
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:8093 --topic test --from-beginning --consumer.config config/client-ssl.properties
```

启动命令行生产者
```
bin/kafka-console-producer.sh --broker-list localhost:8093 --topic test --producer.config config/client-ssl.properties
```
输入消息内容并回车发送消息，观察消费者控制台内容输出。
