package com.xs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CrossConfig {

    @Bean
    public CorsFilter corsFilter(){
        //1. 创建CorsConfiguration对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");//设置允许那些域来访问，*是通配符，允许所有域
        corsConfiguration.addAllowedHeader("*");//请求头字段
        corsConfiguration.addAllowedMethod("*");//请求方式(GET,POST,DELETE,PUT)
        //设置source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);//1.映射路径 2.传入CorsConfiguration对象
        return new CorsFilter(source);
    }
}
