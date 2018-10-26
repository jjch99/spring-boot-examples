package org.example.config.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.StringUtils;

/**
 * 从数据库加载配置参数，需要在{@link Environment}初始化之后，{@link ApplicationContext}初始化之前执行。
 * <p>
 * 通过 {@link EnvironmentPostProcessor} 来加载时，{@link LoggingApplicationListener} 还没有被执行，没有做日志系统的初始化，无法输出日志，这里通过
 * {@link ApplicationEnvironmentPreparedEvent} 来加载。
 *
 */
@Order(Ordered.LOWEST_PRECEDENCE - 1024)
public class DatabasePropertiesLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private void loadProperties(ConfigurableEnvironment environment) {

        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String module = environment.getProperty("spring.application.name");

        // 没有配置数据库则无需处理
        if (StringUtils.isEmpty(driverClassName) || StringUtils.isEmpty(url) || StringUtils.isEmpty(username)) {
            logger.warn("No 'spring.datasource.*', skip load properties from database.");
            return;
        }

        logger.info("Load properties from database ...");

        // 只需单个连接即可，并且用完记得释放
        SingleConnectionDataSource dataSource = null;
        try {
            dataSource = new SingleConnectionDataSource(url, username, password, false);
            dataSource.setDriverClassName(driverClassName);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            // 查询参数所用的SQL
            String sql = null;
            Object[] args = null;
            if (StringUtils.isEmpty(module)) {
                logger.info("No 'spring.application.name', load all properties.");
                sql = " select param_name,param_value,comments from system_param where 1=1";
                args = new Object[] {};
            } else {
                sql = " select param_name,param_value,comments from system_param where module=?";
                args = new Object[] { module };
            }

            final Properties properties = new Properties();
            jdbcTemplate.query(sql, args, new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                    String name = rs.getString(1);
                    String value = rs.getString(2);
                    String comments = rs.getString(3);
                    logger.info(name + "=" + value + " ###" + comments);
                    // 如果配置项有加密之类的，可以先做处理。
                    properties.put(name, value);
                }
            });

            PropertySource propertySource = new PropertiesPropertySource("databaseProperties", properties);
            environment.getPropertySources().addFirst(propertySource);
            logger.info("Load properties from database OK.");

        } catch (DataAccessException e) {
            logger.error("Load properties from database failed.");
            throw e;
        } finally {
            // 释放资源
            if (dataSource != null) {
                dataSource.destroy();
            }
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        loadProperties(environment);
    }

}
