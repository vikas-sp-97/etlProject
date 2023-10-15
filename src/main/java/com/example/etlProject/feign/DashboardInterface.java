package com.example.etlProject.feign;


import com.example.etlProject.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DASHBOARD-SERVICE", configuration = FeignClientConfiguration.class)
public interface DashboardInterface {

    @PostMapping("/register-client")
    public ResponseEntity<?> registerClientUser(@RequestBody User user);

}
