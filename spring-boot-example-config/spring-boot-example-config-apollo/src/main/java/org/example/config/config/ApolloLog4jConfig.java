package org.example.config.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;

/**
 * 在Apollo配置中心设置 日志级别&根日志的Appender，支持热更新
 * <p>
 * 可做成开箱即用的独立的包
 *
 * @see org.springframework.context.ApplicationContextInitializer
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.boot.context.event.ApplicationPreparedEvent
 */
@Component
public class ApolloLog4jConfig implements InitializingBean {

    private static final String LOG4J_APOLLO_NAMESPACE_KEY = "log4j.apollo.namespace";

    private static final String LOG4J_APOLLO_NAMESPACE_DEFAULT_VALUE = "log4j";

    private static final String CONFIG_ITEM_PREFIX = "log4j.logger.";

    private static final String CONFIG_ITEM_ROOT_LOGGER_LEVEL = "log4j.rootLogger.level";

    private static final String CONFIG_ITEM_ROOT_LOGGER_APPENDERREFS = "log4j.rootLogger.appenderRefs";

    private final Logger logger = LogManager.getLogger(getClass());

    /**
     * 保留 rootLogger level 原始值
     */
    private final Level originalRootLevel = LogManager.getRootLogger().getLevel();

    @Autowired
    private Environment env;

    @Override
    public void afterPropertiesSet() {
        log4jConfig();
    }

    /**
     * 设置日志级别
     * 
     * @param loggerName
     * @param levelName
     */
    private void setLoggerLevel(String loggerName, String levelName) {
        Level level = Level.getLevel(StringUtils.upperCase(levelName));
        if (level != null) {
            logger.info("set logger [" + loggerName + "] level to [" + level + "]");
            Configurator.setLevel(loggerName, level);
        }
    }

    /**
     * 设置根日志的日志级别
     * 
     * @param levelName
     */
    private void setRootLoggerLevel(String levelName) {
        Level level = Level.getLevel(StringUtils.upperCase(levelName));
        if (level != null) {
            logger.info("set root logger level to [" + level + "]");
            Configurator.setRootLevel(level);
        }
    }

    /**
     * 设置根日志的输出目标
     * 
     * @param s
     */
    private void setRootLoggerAppenderRefs(String s) {
        if (StringUtils.isEmpty(s)) {
            logger.info("empty 'log4j.rootLogger.appenderRefs' value");
            return;
        }

        String[] appenderNames = StringUtils.split(s, ',');

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration configuration = ctx.getConfiguration();
        Map<String, Appender> appenders = configuration.getAppenders();
        if (!appenders.keySet().containsAll(Arrays.asList(appenderNames))) {
            // 指定的 appender 没有定义
            logger.info("invalid 'log4j.rootLogger.appenderRefs' value: {}", s);
            return;
        }

        logger.info("set rootLogger.appenderRefs to: {}", s);

        LoggerConfig loggerConfig = configuration.getRootLogger();
        List<AppenderRef> refs = loggerConfig.getAppenderRefs();
        refs.forEach(v -> loggerConfig.removeAppender(v.getRef()));
        Stream.of(appenderNames).forEach(v -> loggerConfig.addAppender(appenders.get(v), null, null));
        ctx.updateLoggers();
    }

    /**
     * log4j配置
     */
    private void log4jConfig() {

        String namespace = env.getProperty(LOG4J_APOLLO_NAMESPACE_KEY, LOG4J_APOLLO_NAMESPACE_DEFAULT_VALUE);
        logger.info(LOG4J_APOLLO_NAMESPACE_KEY + "=" + namespace);

        Config config = ConfigService.getConfig(namespace);
        Set<String> props = config.getPropertyNames();
        props.forEach(v -> {
            if (StringUtils.startsWith(v, CONFIG_ITEM_PREFIX)) {
                Level rootLevel = LogManager.getRootLogger().getLevel();
                String loggerName = StringUtils.removeStart(v, CONFIG_ITEM_PREFIX);
                String levelName = config.getProperty(v, rootLevel.name());
                setLoggerLevel(loggerName, levelName);
            } else if (StringUtils.equals(v, CONFIG_ITEM_ROOT_LOGGER_LEVEL)) {
                String loggerName = config.getProperty(v, null);
                setRootLoggerLevel(loggerName);
            } else if (StringUtils.equals(v, CONFIG_ITEM_ROOT_LOGGER_APPENDERREFS)) {
                String refs = config.getProperty(v, null);
                setRootLoggerAppenderRefs(refs);
            }
        });

        config.addChangeListener(e -> {
            Set<String> keys = e.changedKeys();
            keys.forEach(k -> {
                if (StringUtils.startsWith(k, CONFIG_ITEM_PREFIX)) {
                    ConfigChange change = e.getChange(k);
                    String loggerName = StringUtils.removeStart(k, CONFIG_ITEM_PREFIX);
                    String levelName;
                    if (change.getChangeType() == PropertyChangeType.DELETED) {
                        // 配置项被删除，设置为默认日志级别
                        levelName = LogManager.getRootLogger().getLevel().name();
                    } else {
                        levelName = change.getNewValue();
                    }
                    setLoggerLevel(loggerName, levelName);
                } else if (StringUtils.equals(k, CONFIG_ITEM_ROOT_LOGGER_LEVEL)) {
                    ConfigChange change = e.getChange(k);
                    String levelName;
                    if (change.getChangeType() == PropertyChangeType.DELETED) {
                        // 配置项被删除，恢复为配置文件里默认的日志级别
                        levelName = originalRootLevel.name();
                    } else {
                        levelName = change.getNewValue();
                    }
                    setRootLoggerLevel(levelName);
                } else if (StringUtils.equals(k, CONFIG_ITEM_ROOT_LOGGER_APPENDERREFS)) {
                    ConfigChange change = e.getChange(k);
                    if (change.getChangeType() != PropertyChangeType.DELETED) {
                        String refs = change.getNewValue();
                        setRootLoggerAppenderRefs(refs);
                    }
                }
            });
        });
    }

}
