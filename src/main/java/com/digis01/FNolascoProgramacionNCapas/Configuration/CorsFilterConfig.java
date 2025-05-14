package com.digis01.FNolascoProgramacionNCapas.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {
    
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsconfig = new CorsConfiguration();
        
        corsconfig.addAllowedOrigin("*");
        
        corsconfig.addAllowedMethod("GET");
        corsconfig.addAllowedMethod("POST");
        corsconfig.addAllowedMethod("PUT");
        corsconfig.addAllowedMethod("DELETE");
        
        corsconfig.addAllowedHeader("*");
        
        source.registerCorsConfiguration("/**", corsconfig);
        
        return new CorsFilter(source);
    }
    
    
    
}
