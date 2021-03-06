package com.czxy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author :almostTao
 * @date :Created in 2019/6/18 17:29
 */
@Configuration // 标明该类是一个配置类
@ComponentScan(basePackages={"com.czxy"}) // 需要被spring管理的bean所在的包
@PropertySource("classpath:db.properties")	//加载配置文件
@EnableTransactionManagement // 开启事务管理
//@EnableAspectJAutoProxy  //开启切面 另需在切面类上添加@Aspect @Component
public class SpringConfig {
    //解析 ${jdbc.driver} 在 4.2.4中必须配置内容
    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
    /**
     * 获得数据
     */
    @Value("${jdbc.driver}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    /**
     * 配置数据源
     * @return
     */
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    /**
     * 配置模板
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    /**
     * 事务管理器
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}

