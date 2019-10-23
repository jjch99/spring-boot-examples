# 关于日志组件的使用

## 日志门面
早期使用 [commons-logging](http://commons.apache.org/proper/commons-logging/) 的有不少，例如大名鼎鼎的 Spring，就是用的 commons-logging。

现在的主流是 [slf4j](http://www.slf4j.org/)

**一般代码里应该使用日志门面 而不是具体的日志实现**

## 日志实现
早年 [log4j 1.x](http://logging.apache.org/log4j/1.2/)

现在的主流是 [logback](http://logback.qos.ch/) [log4j2](https://logging.apache.org/log4j/2.x/)

**作为公共组件的代码里面一般不要去做日志初始化之类的操作，这些应该交给主程序**

## 其他
由于不同习惯及历史原因，不同程序或库里可能用的日志组件都不一样，难免出现冲突。

关于程序多种日志组件配合使用、日志桥接等介绍，可参考如下文章
> https://www.jianshu.com/p/d7b0e981868d
