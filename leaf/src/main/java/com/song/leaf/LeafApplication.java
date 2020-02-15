package com.song.leaf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
@MapperScans(value = {
		@MapperScan(basePackages = "com.song.leaf.dao01",
				sqlSessionFactoryRef = "sqlSessionFactory1"),

		@MapperScan(basePackages = "com.song.leaf.dao02",
				sqlSessionFactoryRef = "sqlSessionFactory2"),
})
public class LeafApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeafApplication.class, args);
	}

}
