# spring-boot-example-mybatis

#### mybatis结合sharding-sphere分库分表示例
[sharding-sphere-example](https://github.com/sharding-sphere/sharding-sphere-example/tree/3.0.0)

已知问题
- 不支持 replace into
- 多表join的时候，自动把从表转成小写了，如果mysql服务器表名区分大小写而表又不是小写的情况下，会导致找不到表。

问题定位: SQLRewriteEngine#appendTablePlaceholder

#### mybatis-generator 重新生成代码，避免原代码被覆盖
- `generatorConfig.xml` 中 `sqlMapGenerator`、`javaClientGenerator` 配置 `targetProject="MAVEN"`，生成的 `Mapper.xml` `Mapper
.java` 将位于 `target/generated-sources` 中，然后手工复制过去（由于需要重新生成这些文件的机会较少，生成后手工复制是可接受的）。

- 所有自定义SQL放在单独的文件中，如原Mapper文件名为XxxMapper.xml，自定义SQL的Mapper文件可命名为XxxMapperExt.xml，
  两个文件采用相同的namespace，MapperExt.xml文件里即可以引用Mapper.xml中定义的内容。

- Mapper.xml 和 MapperExt.xml 共用一个 Mapper.java，代码重新生成时一般不需要替换 Mapper.java

- 自定义SQL在Mapper.java中新增的方法务必写上注释，以便区分哪些是自定义的SQL。

#### 避免 `mvn package` 的时候执行 mybatis-generator-maven-plugin 而重复生成代码
在插件配置中指定 `<phase>none</phase>`，参考如下
```xml
<executions>
    <execution>
        <id>Generate MyBatis Artifacts</id>
        <goals>
            <goal>generate</goal>
        </goals>
        <!-- 不绑定到生命周期的任何阶段，只在手工指定命令时运行 -->
        <phase>none</phase>
    </execution>
</executions>
```