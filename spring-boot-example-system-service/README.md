# Spring Boot程序安装为系统服务
官方说明
> https://docs.spring.io/spring-boot/3.5/how-to/deployment/installing.html

关于Windows环境Java程序安装为系统服务,常见方案和工具

1.Apache commons-daemon, 代表应用: Tomcat
> http://commons.apache.org/proper/commons-daemon/
>
> http://commons.apache.org/proper/commons-daemon/procrun.html

2.winsw: Windows service wrapper
> https://github.com/kohsuke/winsw

---

Linux环境守护进程工具
- [daemontools](http://cr.yp.to/daemontools.html) - [supervise](http://cr.yp.to/daemontools/supervise.html)
- [Monit](https://mmonit.com/monit/)
- [Supervisor](http://supervisord.org/)
