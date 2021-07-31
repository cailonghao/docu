package com.docu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients(basePackages = {
        "com.mem.open"
})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.docu.auth",
        "com.mem.open"
})
public class DocuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocuAuthApplication.class, args);
    }

}
