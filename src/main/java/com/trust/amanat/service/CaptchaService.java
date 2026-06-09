package com.trust.amanat.service;

import com.trust.amanat.dto.CaptchaDTO;

public interface CaptchaService {
    CaptchaDTO generateCaptcha();
    boolean verifyCaptcha (String captchaId, String captchaValue) ;
}
