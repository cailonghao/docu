package com.docu.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {


    @Value("${server.port}")
    String port;

    @RequestMapping("/listService")
    public String listService() {
        System.out.println("server port is " + port);
        return "server port is " + port;
    }

    @PostMapping("/getName")
    public String getName() throws InterruptedException {
        Thread.sleep(4*1000);
        return "auth message --> " + port;
    }
}
