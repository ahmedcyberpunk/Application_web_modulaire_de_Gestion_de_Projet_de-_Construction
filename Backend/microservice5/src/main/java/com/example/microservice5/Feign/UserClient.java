package com.example.microservice5.Feign;


import com.example.microservice5.Dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microserviceUser", url = "http://localhost:8088/*")
public interface UserClient {

}