package com.zqw.gmh.count.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactorySecond",
        transactionManagerRef="transactionManagerSecond",
        basePackages= { "com.zqw.gmh.count.domain.s" }) //设置Repository所在位置
public class SecondConfig {
	@Autowired
	@Qualifier("secondDataSource")
	private DataSource secondDataSource;
	
	 @Bean(name = "entityManagerSecond")
	 public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
	     return entityManagerFactorySecond(builder).getObject().createEntityManager();
	 }
	 
	 @Bean(name = "entityManagerFactorySecond")
	 public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond (EntityManagerFactoryBuilder builder) {
	     return builder
	             .dataSource(secondDataSource)
	             .properties(getVendorProperties(secondDataSource))
	             .packages("com.zqw.gmh.count.domain.s") //设置实体类所在位置
	             .persistenceUnit("secondPersistenceUnit")
	             .build();
	 }
	 @Autowired
	 private JpaProperties jpaProperties;

	 private Map<String, String> getVendorProperties(DataSource dataSource) {
	     return jpaProperties.getHibernateProperties(dataSource);
	 }
	 
	 
	 @Bean(name = "transactionManagerSecond")
	 public PlatformTransactionManager transactionManagerSecond(EntityManagerFactoryBuilder builder) {
	     return new JpaTransactionManager(entityManagerFactorySecond(builder).getObject());
	 }
}
