//package com.muravlev.core.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
////    @Bean
////    public CorsFilter corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);
////        config.addAllowedOrigin("localhost:3000"); // Разрешенный домен
////        config.addAllowedHeader("*"); // Разрешить все заголовки
////        config.addAllowedMethod("*"); // Разрешить все методы
////        source.registerCorsConfiguration("/**", config);
////        return new CorsFilter(source);
////    }
//
////    @Bean
////    public FilterRegistrationBean<CorsFilter> corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);
////        config.addAllowedOrigin("http://localhost:3000/");
////        config.addAllowedHeader("*");
////        config.addAllowedMethod("*"); // Также разрешаем OPTIONS запросы
////        source.registerCorsConfiguration("/**", config);
////        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
////        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
////        return bean;
////    }
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }
//
//}
