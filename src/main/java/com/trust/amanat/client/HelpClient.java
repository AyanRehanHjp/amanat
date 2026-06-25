package com.trust.amanat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "AMANAT-HELP-SERVICE")
public interface HelpClient {

    @GetMapping("/api/help-distributed")
    String getAllHelpData();
}
