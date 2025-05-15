package com.example.microservice2.feign;

import com.example.microservice2.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microserviceUser", url = "http://localhost:8088/auth")
public interface UserClient {



    @PostMapping("/login")
    UserDTO login(@RequestBody UserDTO userDTO);

    @GetMapping("/me")
    UserDTO getCurrentUser(@RequestHeader("Authorization") String token);
}