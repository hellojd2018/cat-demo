package com.example.demo;

import com.dianping.cat.aop.CatAnnotationAopService;
import com.dianping.cat.plugins.CatMybatisInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {
	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@Bean
	public CatMybatisInterceptor newCatMybatisInterceptor(){

		return new CatMybatisInterceptor(datasourceUrl);
	}
	@Bean
	public CatAnnotationAopService newCatAopService(){
		return new CatAnnotationAopService();
	}

}
