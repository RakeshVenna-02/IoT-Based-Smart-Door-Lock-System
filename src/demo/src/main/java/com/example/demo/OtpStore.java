package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class OtpStore {
   private String otp;
   private long expirytime;
   public OtpStore(){

   }
   public OtpStore(String otp,long expirytime){
       this.otp=otp;
       this.expirytime=expirytime;
   }
   public String getOtp(){
       return otp;
   }
   public void setotp(String otp){
       this.otp=otp;
   }
   public long getExpirytime(){
       return expirytime;
   }
   public void setExpirytime(long expirytime){
       this.expirytime=expirytime;
   }
}
