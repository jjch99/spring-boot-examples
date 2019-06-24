package org.example.config;

import java.net.URI;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

/**
 * 采用Apollo来配置log4j2
 * <p>
 * Example:
 * 
 * <pre>
 * status = debug
 * name = PropertiesConfig
 *
 * filters = threshold
 *
 * filter.threshold.type = ThresholdFilter
 * filter.threshold.level = debug
 *
 * appenders = console
 *
 * appender.console.type = Console
 * appender.console.name = STDOUT
 * appender.console.layout.type = PatternLayout
 * appender.console.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L ---- %m%n
 *
 * rootLogger.level = info
 * rootLogger.appenderRefs = stdout
 * rootLogger.appenderRef.stdout.ref = STDOUT
 * </pre>
 */
@Plugin(name = "ApolloLog4j2ConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class ApolloLog4j2ConfigurationFactory extends ConfigurationFactory {

    private static final String LOG4J2_NAMESPACE = "log4j";

    @Override
    protected String[] getSupportedTypes() {
        return new String[] { "*" };
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        // 从Apollo获取log4j2配置
        Config config = ConfigService.getConfig(LOG4J2_NAMESPACE);
        Properties properties = getProperties(config);

        // 增加监听器，配置修改后重新加载
        config.addChangeListener(e -> {
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            ctx.reconfigure();
        });

        // 构造log4j2的Configuration
        return new PropertiesConfigurationBuilder()
                .setRootProperties(properties)
                .setLoggerContext(loggerContext)
                .build();
    }

    private Properties getProperties(Config config) {
        Set<String> propertyNames = config.getPropertyNames();
        Properties properties = new Properties();
        for (String propertyName : propertyNames) {
            String propertyValue = config.getProperty(propertyName, null);
            properties.setProperty(propertyName, propertyValue);
        }
        return properties;
    }

}