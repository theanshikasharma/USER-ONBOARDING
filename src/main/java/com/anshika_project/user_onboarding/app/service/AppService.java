package com.anshika_project.user_onboarding.app.service;

import com.anshika_project.user_onboarding.app.HashUtil;
import com.anshika_project.user_onboarding.app.dto.CredentialDto;
import com.anshika_project.user_onboarding.app.model.Credential;
import com.anshika_project.user_onboarding.app.model.User;
import com.anshika_project.user_onboarding.app.repository.CredentialRepository;
import com.anshika_project.user_onboarding.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AppService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private OtpService otpService;

    /**
     * Public method to generate OTP (synchronous response, async processing)
     */
    public Map<String, String> generateOtp(String phone) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        Instant expiryTime = Instant.now().plus(5, ChronoUnit.MINUTES);

        // Asynchronous save and log
        asyncGenerateOtp(phone, otp, expiryTime);

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP generated successfully");
        response.put("otp", otp); // Dev only, hide in production
        return response;
    }

    /**
     * Asynchronously persist OTP data to DB
     */
    @Async
    public void asyncGenerateOtp(String phone, String otp, Instant expiryTime) {
        otpService.generateOtp(phone, otp, expiryTime);
        System.out.println("[ASYNC] Generated OTP for " + phone + ": " + otp);
    }

    /**
     * Verify OTP
     */
    public boolean verifyOtp(String phone, String otp) {
        return otpService.verifyOtp(phone, otp);
    }

    /**
     * Validate name contains only letters and spaces
     */
    public boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-Z ]+$");
    }

    /**
     * Validate gender
     */
    public boolean isValidGender(String gender) {
        return gender != null && (
                gender.equalsIgnoreCase("male") ||
                        gender.equalsIgnoreCase("female") ||
                        gender.equalsIgnoreCase("other")
        );
    }

    /**
     * Save user info to MongoDB
     */
    public void saveUser(User user) {
        System.out.println("[DEBUG] Saving user: " + user);
        userRepository.save(user);
    }

    /**
     * Fetch all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Fetch a user by ID
     */
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Delete user by ID
     */
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if username already exists
     */
    public boolean usernameExists(String username) {
        return credentialRepository.findByUsername(username).isPresent();
    }

    /**
     * Save credential info with SHA-256 hashing
     */
    public void saveCredential(CredentialDto dto) {
        Credential credential = new Credential();
        credential.setUsername(dto.getUsername());
        credential.setPassword(HashUtil.sha256(dto.getPassword()));
        credential.setCreated(Instant.now().toString());
        credentialRepository.save(credential);
    }

    /**
     * Login validation: check if username & password match
     */
    public boolean login(String username, String password) {
        Optional<Credential> optionalCredential = credentialRepository.findByUsername(username);
        if (optionalCredential.isPresent()) {
            String hashedPassword = HashUtil.sha256(password);
            return optionalCredential.get().getPassword().equals(hashedPassword);
        }
        return false;
    }
}
