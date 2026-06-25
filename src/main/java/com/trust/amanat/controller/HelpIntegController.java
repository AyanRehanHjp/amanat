package com.trust.amanat.controller;

import com.trust.amanat.client.HelpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/eureka")
public class HelpIntegController {

    @Autowired
    HelpClient helpClient;


//    @Autowired
//    RestTemplate restTemplate;

//    @GetMapping("/help-test")
//    public String helpTest() {
//        String url = "http://AMANAT-HELP-SERVICE/api/help-distributed";
//        return restTemplate.getForObject(url, String.class);
//    }

    @GetMapping("/help-feign")
    public String helpFeign() {
        return helpClient.getAllHelpData();
    }
}
