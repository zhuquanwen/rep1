package com.zqw.gmh.count.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig {
	@Value("${spring.datasource.primary.url}")  
	private String url; 
	@Value("${spring.datasource.primary.driver-class-name}")  
	private String driver_class_name;  
	@Value("${spring.datasource.primary.username}")  
	private String username;  
	@Value("${spring.datasource.primary.password}")  
	private String password;  
	@Value("${spring.datasource.primary.maxActive}")  
	private Integer maxActive;  
	@Value("${spring.datasource.primary.initialSize}")  
	private Integer initialSize;  
	@Value("${spring.datasource.primary.maxWait}")  
	private Long maxWait;  
	@Value("${spring.datasource.primary.minIdle}")  
	private Integer minIdle;  
	@Value("${spring.datasource.primary.timeBetweenEvictionRunsMillis}")  
	private Long timeBetweenEvictionRunsMillis;  
	@Value("${spring.datasource.primary.minEvictableIdleTimeMillis}")  
	private Long minEvictableIdleTimeMillis;  
	@Value("${spring.datasource.primary.validationQuery}")  
	private String validationQuery; 
	@Value("${spring.datasource.primary.testWhileIdle}")  
	private Boolean testWhileIdle; 
	@Value("${spring.datasource.primary.testOnBorrow}")  
	private Boolean testOnBorrow; 
	@Value("${spring.datasource.primary.testOnReturn}")  
	private Boolean testOnReturn; 
	@Value("${spring.datasource.primary.poolPreparedStatements}")  
	private Boolean poolPreparedStatements; 
	@Value("${spring.datasource.primary.maxOpenPreparedStatements}")  
	private Integer maxOpenPreparedStatements; 
	@Value("${spring.datasource.primary.maxPoolPreparedStatementPerConnectionSize}")  
	private Integer maxPoolPreparedStatementPerConnectionSize; 
	@Value("${spring.datasource.primary.filters}")  
	private String filters; 
	@Value("${spring.datasource.primary.connectionProperties}")  
	private String connectionProperties; 
	@Value("${spring.datasource.primary.useGlobalDataSourceStat}")  
	private Boolean useGlobalDataSourceStat; 
	
	
	@Bean(name="primaryDataSource")
	@Qualifier("primaryDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource.primary")
	public DataSource primaryDataSource() throws Exception{
		/*DataSource ds= DataSourceBuilder.create().build();
		return ds;*/
		//上面使用默认的连接池，下面修改为使用druid
		DruidDataSource dds=new DecryptDruidSource();
		dds.setUrl(url);
		dds.setDriverClassName(driver_class_name);
		/*dds.setUsername(username);
		dds.setPassword(password);*/
		//使用加密的用户名密码
		dds.setUsername(username);
		dds.setPassword(password);
		dds.setMaxActive(maxActive);
		dds.setInitialSize(initialSize);
		dds.setMaxWait(maxWait);
		dds.setMinIdle(minIdle);
		dds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dds.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dds.setValidationQuery(validationQuery);
		dds.setTestWhileIdle(testWhileIdle);
		dds.setTestOnBorrow(testOnBorrow);
		dds.setTestOnReturn(testOnReturn);
		dds.setPoolPreparedStatements(poolPreparedStatements);
		dds.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		dds.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		dds.setFilters(filters);
		dds.setConnectionProperties(connectionProperties);
		dds.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		return dds;
	}
	
	@Bean(name="secondDataSource")
	@Qualifier("secondDataSource")
	@ConfigurationProperties(prefix="spring.datasource.second")
	public DataSource secondDataSource(){
		return DataSourceBuilder.create().build();
	}
}
