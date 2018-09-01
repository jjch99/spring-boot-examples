#### 关于动态数据源

> 思路：使用 AbstractingRoutingDataSource，包装多个数据源，然后通过AOP拦截相关的方法，执行方法前，设置所采用的数据源，执行结束后清理或还原。

##### 常用方法：
- 通过自定义注解形式指定类或方法所采用的数据源，无指定则使用默认数据源。
- 根据方法名称选择数据源，如insert/update/save/write等操作使用主库，select/find/query等操作采用从库，只读的话可以采用负载均衡算法选择。
- 通过方法参数指定使用的数据源，选择哪个数据源根据业务层面分片机制决定。

##### 使用动态数据源要考虑的问题
- 事务，一致性。
- 应用节点过多导致数据库连接数量上升。
- 每个应用使用的数据源太多容易混乱。对同类数据的sharding最好还是通过专用工具、中间件来处理。业务功能上采用服务拆分的方式来处理。

##### 参考
> SpringBoot + Mybatis + Druid多数据源集成的心得  
> https://my.oschina.net/u/237688/blog/1602693

> SpringBoot + Mybatis多数据源和动态数据源配置  
> https://blog.csdn.net/neosmith/article/details/61202084
