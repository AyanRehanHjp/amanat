package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.CaptchaDTO;
import com.trust.amanat.service.CaptchaService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final Map <String,String> captchaStore = new ConcurrentHashMap<>();
    public CaptchaDTO generateCaptcha() {
        String captchaId = UUID.randomUUID().toString();
        String captcha =   UUID.randomUUID().toString().replace("_","").substring(0, 4).toUpperCase();
        captchaStore.put(captchaId, captcha);
        CaptchaDTO captchaDTO  = new CaptchaDTO();
        captchaDTO.setCaptchaId(captchaId);
        captchaDTO.setCaptcha(captcha);
        // Generate a random 4-character captcha

        return captchaDTO;
    }

    public boolean verifyCaptcha (String captchaId, String captchaValue) {
        String actualCaptcha = captchaStore.get(captchaId);
        if (actualCaptcha == null ) {
            return false;
        }
            captchaStore.remove(captchaId); // Remove after verification
            return actualCaptcha.equalsIgnoreCase(captchaValue);

    }
}
