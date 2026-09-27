package com.anshika_project.user_onboarding.app.service;

import com.anshika_project.user_onboarding.app.dto.UserDto;
import com.anshika_project.user_onboarding.app.model.User;
import com.anshika_project.user_onboarding.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setMobileNumber(userDto.getMobileNumber());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setCity(userDto.getCity());
        userRepository.save(user);
    }
}
