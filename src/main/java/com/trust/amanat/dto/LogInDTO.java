package com.trust.amanat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInDTO {
    private String userName;
    private String password;
    private String captchaId;
    private String captchaValue;
}
