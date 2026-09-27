package com.anshika_project.user_onboarding.app.repository;

import com.anshika_project.user_onboarding.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
}



