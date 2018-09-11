package com.example.web1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.dao1")
@ComponentScan(basePackages={"com.example.web1","com.example.dao1"})
public class Web1Application {

	public static void main(String[] args) {
		SpringApplication.run(Web1Application.class, args);
	}
}
