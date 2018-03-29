package com.zworks.pdsys.datasources;
//package com.hyron.datasources;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 配置多数据源
// * @author ZHAI
// * @date 2018/03/27 0:41
// */
//@Configuration
//public class DynamicDataSourceConfig {
//
//	@Bean
//	@ConfigurationProperties("spring.datasource.druid.first")
//	public DataSource firstDataSource() {
//		return DruidDataSourceBuilder.create().build();
//	}
//
//	@Bean
//	@Primary
//	public DynamicDataSource dataSource(DataSource firstDataSource, DataSource secondDataSource) {
//		Map<String, DataSource> targetDataSources = new HashMap<>();
//		targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
//		return new DynamicDataSource(firstDataSource, targetDataSources);
//	}
//}
