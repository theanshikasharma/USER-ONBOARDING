package com.anshika_project.user_onboarding.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credentials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credential {

    @Id
    private String id;

    private String username;
    private String password;  // Preferably store hashed password (never plain-text)
    private String created;
}
