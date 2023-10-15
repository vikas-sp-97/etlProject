package com.example.etlProject.feign;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Retrieve the JWT token from your authentication mechanism
            String jwtToken = "Bearer " + retrieveJwtToken();

            // Add the JWT token to the request header
            requestTemplate.header("Authorization", jwtToken);
        };
    }

    public String retrieveJwtToken() {
        // Get the current HTTP request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // Retrieve the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part after "Bearer "
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
