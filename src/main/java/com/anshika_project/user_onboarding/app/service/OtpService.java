package com.anshika_project.user_onboarding.app.service;

import com.anshika_project.user_onboarding.app.HashUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    // Inner class to store hashed OTP and expiry time
    private static class OtpDetails {
        private final String hashedOtp;
        private final Instant expiryTime;

        public OtpDetails(String hashedOtp, Instant expiryTime) {
            this.hashedOtp = hashedOtp;
            this.expiryTime = expiryTime;
        }

        public String getHashedOtp() {
            return hashedOtp;
        }

        public Instant getExpiryTime() {
            return expiryTime;
        }
    }

    // Thread-safe map to store OTPs
    private final ConcurrentHashMap<String, OtpDetails> otpStore = new ConcurrentHashMap<>();

    /**
     * Stores a hashed OTP for a phone number with expiry time.
     * @param phone - the phone number
     * @param otp - the raw OTP
     * @param expiryTime - expiry time in Instant
     * @return raw OTP (can be sent to frontend or SMS service)
     */
    public String generateOtp(String phone, String otp, Instant expiryTime) {
        String hashedOtp = HashUtil.sha256(otp);
        otpStore.put(phone, new OtpDetails(hashedOtp, expiryTime));
        return otp;
    }

    /**
     * Verifies the user-provided OTP for the given phone number.
     * @param phone - phone number used to retrieve OTP
     * @param inputOtp - raw OTP provided by user
     * @return true if OTP is valid and not expired
     */
    public boolean verifyOtp(String phone, String inputOtp) {
        if (phone == null || inputOtp == null) return false;

        OtpDetails details = otpStore.get(phone);
        if (details == null) return false;

        if (Instant.now().isAfter(details.getExpiryTime())) {
            otpStore.remove(phone); // remove expired
            return false;
        }

        boolean match = details.getHashedOtp().equals(HashUtil.sha256(inputOtp));
        if (match) {
            otpStore.remove(phone); // OTP is one-time use
        }
        return match;
    }
}
