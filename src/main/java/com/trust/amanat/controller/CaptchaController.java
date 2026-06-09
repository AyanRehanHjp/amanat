package com.trust.amanat.controller;

import com.trust.amanat.dto.CaptchaDTO;
import com.trust.amanat.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    CaptchaService captchaService;

    @GetMapping("/generate")
    public CaptchaDTO generateCaptcha() {
        return captchaService.generateCaptcha();

    }
}
