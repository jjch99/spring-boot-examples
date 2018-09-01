
RabbitMQ Docs
> http://www.rabbitmq.com/documentation.html

RabbitMQ 下载
> http://www.rabbitmq.com/releases/rabbitmq-server/

RabbitMQ 延迟消息
> https://www.jb51.net/article/139457.htm

一些概念
> https://github.com/401Studio/WeekLearn/issues/2

mac 下安装
```
brew install rabbitmq
brew services run rabbitmq
/usr/local/sbin/rabbitmq-plugins enable rabbitmq_management
open http://localhost:15672/
# username: guest 
# password: guest
```

CentOS
```
yum install erlang
wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.15/rabbitmq-server-3.6.15-1.el6.noarch.rpm
yum install rabbitmq-server-3.6.15-1.el6.noarch.rpm
service rabbitmq-server start
service rabbitmq-server status
```
