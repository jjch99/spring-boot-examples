
RabbitMQ Docs
> http://www.rabbitmq.com/documentation.html

RabbitMQ 下载
> http://www.rabbitmq.com/releases/rabbitmq-server/

RabbitMQ 延迟消息
> https://www.jb51.net/article/139457.htm

一些概念
> https://github.com/401Studio/WeekLearn/issues/2

CentOS
```
yum install erlang
wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.15/rabbitmq-server-3.6.15-1.el6.noarch.rpm
yum install rabbitmq-server-3.6.15-1.el6.noarch.rpm
service rabbitmq-server start
service rabbitmq-server status
```

Docker
```bash
docker pull rabbitmq:3.6.15-management
docker run -d --name rabbitmq-server -p 5672:5672 -p 15672:15672 -v $HOME/data/rabbitmq:/var/lib/rabbitmq --hostname rabbitmq-server -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest666 rabbitmq:3.6.15-management
```

管理控制台: http://localhost:15672/
