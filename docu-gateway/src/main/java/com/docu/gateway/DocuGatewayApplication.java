package com.docu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DocuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocuGatewayApplication.class, args);
    }

}
