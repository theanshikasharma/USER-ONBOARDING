package com.anshika_project.user_onboarding.app.dto;

public class UserDto {
    private String mobileNumber;
    private String name;
    private int age;
    private String gender;
    private String city;

    // Getters
    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    // Setters
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
