package com.study.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// 스프링은 @Configuration이 지정된 클래스를 자바 기반의 설정 파일로 인식
@Configuration
// 해당 클래스에서 참조할 properties 파일의 위치를 지정
@PropertySource("classpath:/application.properties")
public class DatabaseConfig {

	// 히카리CP 객체를 생성, 히카리CP는 커넥션 풀(Connection Pool) 라이브러리 중 하나
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    // 데이터 소스 객체를 생성
    /* 순수 JDBC는 SQL을 실행할 때마다 커넥션을 맺고 끊는 I/O 작업을 함
     * 이러한 작업은 상당한 리소스를 잡아 먹음
     * 해결책 : 커넥션 풀
     * 커넥션 풀은 커넥션 객체를 생성해두고, DB에 접근하는 사용자에게 미리 생성해둔 커넥션을 제공했다가
     * 다시 돌려받는 방법
     * 
     * 데이터 소스는 커넥션 풀을 지원하기 위한 인터페이스
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

}