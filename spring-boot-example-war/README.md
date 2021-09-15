# spring-boot-example-war
本例子将应用打war包，class文件等文件打成jar包放到WEB-INF/lib目录，保留配置文件在WEB-INF/classes目录

## Tomcat7/8部署时启动异常缓慢问题
Tomcat日志提示信息如下
```text
org.apache.catalina.util.SessionIdGeneratorBase.createSecureRandom Creation of SecureRandom instance for session ID generation using [SHA1PRNG] took [151,633] milliseconds.
```
问题原因

SecureRandom 这个JRE的工具类存在问题。SecureRandom generateSeed慢是因为Tomcat7、Tomcat8服务都是使用org.apache.catalina.util.SessionIdGeneratorBase.createSecureRandom类产生安全随机类SecureRandom的实例作为会话ID。

解决方法

`catalina.sh`增加如下
```text
CATALINA_OPTS="-Djava.security.egd=file:///dev/urandom"
```
