package com.anshika_project.user_onboarding.app.repository;

import com.anshika_project.user_onboarding.app.model.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CredentialRepository extends MongoRepository<Credential, String> {
    Optional<Credential> findByUsername(String username);
}

