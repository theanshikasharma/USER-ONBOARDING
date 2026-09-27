package com.anshika_project.user_onboarding.app.dto;

public class OtpRequest {
    private String mobileNumber;
    private String otp; // Only needed for OTP verification

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
