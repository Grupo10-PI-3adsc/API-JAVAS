package com.example.CRUD.security.CORS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("http://localhost:5173"); // Especifique o frontend
        config.addAllowedOrigin("http://localhost:3000"); // Especifique o frontend
        config.addAllowedOrigin("http://10.0.2.2:8080");
        config.addAllowedOrigin("http://10.0.3.2:8080");
        config.addAllowedOrigin("http://52.203.203.23:8080");
        config.addAllowedOrigin("http://52.203.203.23:3000");
        config.addAllowedOrigin("http://52.203.203.23");
        config.addAllowedOrigin("https://purple-meadow-0d15dc30f.4.azurestaticapps.net"); // Especifique o frontend

        config.addAllowedOriginPattern("http://localhost:*");
        config.addAllowedOriginPattern("http://10.0.1.*");
        config.addAllowedOriginPattern("http://10.0.0.*");

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}