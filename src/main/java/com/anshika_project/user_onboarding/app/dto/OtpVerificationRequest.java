package com.anshika_project.user_onboarding.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationRequest {
    private String phone;
    private String otp;
    private String name;
    private Integer age;
    private String gender;
    private String city;
    private String mobile; // Changed from Number to String for better JSON compatibility
}
