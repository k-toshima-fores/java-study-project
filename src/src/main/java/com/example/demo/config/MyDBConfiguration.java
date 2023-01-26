package com.example.demo.config;

import javax.sql.DataSource;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.mysql")
@MapperScan(basePackages = "com.example.demo.mapper.mysql", sqlSessionFactoryRef  = "mysqlSqlSessionFactory")
public class MyDBConfiguration {
  private String driverClassName;
  private String url;
  private String username;
  private String password;

  @Bean(name = "mysqlDataSource")
  public DataSource createDataSource() {
    return DataSourceBuilder
        .create()
        .driverClassName(driverClassName)
        .url(url)
        .username(username)
        .password(password)
        .build();
  }

  @Bean(name = "mysqlJdbc")
  public JdbcTemplate createJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean(name = "mysqlSqlSessionFactory")
  public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
    return bean.getObject();
  }

  @Bean(name = "mysqlSqlSessionTemplate")
  public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
