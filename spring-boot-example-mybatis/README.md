# spring-boot-example-mybatis

#### mybatis结合sharding-sphere分库分表示例
[sharding-sphere-example](https://github.com/sharding-sphere/sharding-sphere-example/tree/3.0.0)

已知问题
- 不支持 replace into
- 多表join的时候，自动把从表转成小写了，如果mysql服务器表名区分大小写而表又不是小写的情况下，会导致找不到表。

问题定位: SQLRewriteEngine#appendTablePlaceholder
