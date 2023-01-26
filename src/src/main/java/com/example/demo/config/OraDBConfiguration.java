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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.oracle")
@MapperScan(basePackages = "com.example.demo.mapper.oracle", sqlSessionFactoryRef  = "oracleSqlSessionFactory")
public class OraDBConfiguration {
  private String driverClassName;
  private String url;
  private String username;
  private String password;

  @Bean(name = "oracleDataSource")
  @Primary
  public DataSource createDataSource() {
    return DataSourceBuilder
        .create()
        .driverClassName(driverClassName)
        .url(url)
        .username(username)
        .password(password)
        .build();
  }

  @Bean(name = "oracleJdbcTemplate")
  @Primary
  public JdbcTemplate createJdbcTemplate(@Qualifier("oracleDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean(name = "oracleSqlSessionFactory")
  public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
    return bean.getObject();
  }

  @Bean(name = "oracleSqlSessionTemplate")
  public SqlSessionTemplate oracleSqlSessionTemplate(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

}
