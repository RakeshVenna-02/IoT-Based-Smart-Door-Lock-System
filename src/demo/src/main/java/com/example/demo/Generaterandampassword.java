package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
@RestController
public class Generaterandampassword {
    @Autowired
    private OtpStore otpStore;
    private static final String Master_Password = "Rakesh9573@";
    private final SecureRandom random = new SecureRandom();


    @GetMapping("/api/password")
    public String getPassword() {
        return Master_Password;

    }

    private String generateOtp() {
        String otp = String.valueOf(100000 + random.nextInt(900000));
        long expirytime = System.currentTimeMillis() + 5 * 60 * 1000;
        otpStore.setotp(otp);
        otpStore.setExpirytime(expirytime);
        System.out.println(otp);
        return otp;
    }
    @GetMapping("/api/generate-otp")
    public String createOtp() {
        return generateOtp();
    }
    @GetMapping("/api/otp")
    public String getCurrentOtp() {
        if (otpStore != null && System.currentTimeMillis() < otpStore.getExpirytime()) {
            return otpStore.getOtp();
        } else {
            return "EXPIRED";
        }

    }
}
