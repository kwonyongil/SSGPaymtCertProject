package com.example.SSGPaymtCertProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication :
 * SpringBootConfiguration, ComponentScan, EnableAutoConfiguration 3개 포함
 * 스프링 부트는 AutoConfiguration(자동 설정) 어노테이션이 있고
 * 애플리케이션 개발에 필요한 모든 내부 Dependency 를 관리
 * 스프링의 jar 파일이 클래스 Path 에 있는 경우 Spring Boot는 dispatcher servlet 으로 자동 구성합니다.
 * 예시로 Hibernate 의 jar 파일이 클래스 Path 에 존재한다면 이를 Datasource 로 자동설정하게 됩니다.
 * 스프링 부트는 SpringBoot Starter 를 도입하여
 * MVC, Jackson, Databind, Hibernate 등의 의존성에 버전 호환성 문제를 해결하고
 * 이러한 버전 호환성 이슈를 개발자가 신경 쓸 필요없이 SpringBoot Starter 를 도입하여 의존성을 관리
 */
@SpringBootApplication
public class SsgPaymtCertProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgPaymtCertProjectApplication.class, args);
	}

}
