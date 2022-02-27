package com.example.SSGPaymtCertProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @since 2022. 02. 25
 * @author kwon-yong-il
 *         <h3>App 설정 파일</h3>
 */
@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
