package com.apipulse.monitor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8081", path = "/internal/users")
public interface AuthClient {

    @GetMapping("/by-email")
    Long checkUser(@RequestParam String email);
}
