package com.mem.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "com.client.auth",
        "com.mem.provider"
})
@EnableFeignClients(basePackages = {
        "com.client.auth"
})
@EnableDiscoveryClient
@EntityScan(basePackages = "com.mem.api.pojo")
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
