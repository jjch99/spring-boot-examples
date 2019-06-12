## Elasticsearch 简单安装

调整Linux系统`limits`配置 (如果仅绑定地址`127.0.0.1`，无需调整系统参数)
```
sudo vi /etc/security/limits.conf
```

```
* hard memlock unlimited
* soft memlock unlimited
* hard nofile 65536
* soft nofile 65536
*  -   as unlimited
```

调整系统参数
```
sudo vi /etc/sysctl.conf
```

```
fs.file-max = 2097152
vm.max_map_count = 262144
vm.swappiness = 1
```

```
sudo sysctl -p
```

退出用户重新登录。


ES 参数配置
```
vi config/elasticsearch.yml
```

```
cluster.name: my-elasticsearch-cluster
node.name:  node1
network.host: 0.0.0.0
http.port: 9200
```

显示命令参数
```
./elasticsearch -h
```

启动 ES
```
# 前台运行
./elasticsearch

# 后台运行
./elasticsearch -d
```