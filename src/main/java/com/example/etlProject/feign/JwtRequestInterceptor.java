package com.example.etlProject.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Get the JWT token from the security context
        String jwtToken = extractJwtTokenFromSecurityContext();

        if (jwtToken != null) {
            // Add the JWT token to the request headers
            requestTemplate.header("Authorization", "Bearer " + jwtToken);
        }
    }

    private String extractJwtTokenFromSecurityContext() {
        // Extract the JWT token from the SecurityContext (Spring Security)
        // Replace this logic with your actual token extraction code
        // For example, if you are using JWTs, you can extract the token from the Authentication object.
        // You may also need to modify this code to fit your specific authentication mechanism.

        // Here, we're assuming that the JWT token is stored in the security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return (String) principal;
        }
        return null;
    }
}

