package com.anshika_project.user_onboarding.app.controller;
import com.anshika_project.user_onboarding.app.dto.ApiResponse;
import com.anshika_project.user_onboarding.app.dto.CredentialDto;
import com.anshika_project.user_onboarding.app.dto.OtpRequest;
import com.anshika_project.user_onboarding.app.model.User;
import com.anshika_project.user_onboarding.app.service.AppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})

public class AuthController {

    @Autowired
    private AppService service;

    // 1. Generate OTP
    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @PostMapping("/generate-otp")
    public ResponseEntity<Map<String, String>> generateOtp(@RequestBody OtpRequest request) {
        Map<String, String> response = service.generateOtp(request.getMobileNumber());
        return ResponseEntity.ok(response);
    }

    // 2. Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest req) {
        boolean valid = service.verifyOtp(req.getMobileNumber(), req.getOtp());
        return valid
                ? ResponseEntity.ok(new ApiResponse(true, "OTP verified"))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Invalid OTP"));
    }

    // 3. Register User (after OTP verification)
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (!service.isValidName(user.getName())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid name"));
        }

        if (!service.isValidGender(user.getGender())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid gender"));
        }

        service.saveUser(user);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    // 4. Save username and hashed password
    @PostMapping("/save-credentials")
    public ResponseEntity<?> saveCredentials(@RequestBody CredentialDto dto) {
        if (service.usernameExists(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(false, "Username already taken"));
        }

        service.saveCredential(dto);
        return ResponseEntity.ok(new ApiResponse(true, "Credentials saved successfully"));
    }

    // 5. Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDto dto) {
        boolean isValid = service.login(dto.getUsername(), dto.getPassword());
        return isValid
                ? ResponseEntity.ok(new ApiResponse(true, "Login successful"))
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid credentials"));
    }

    // 6. Verify and Save Combined API
    @PostMapping("/verify-and-save")
    public ResponseEntity<?> verifyAndSave(@RequestBody User user) {
        if (!service.isValidName(user.getName())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid name"));
        }

        if (!service.isValidGender(user.getGender())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid gender"));
        }

        service.saveUser(user);
        return ResponseEntity.ok(new ApiResponse(true, "User saved successfully via verify-and-save"));
    }
}
