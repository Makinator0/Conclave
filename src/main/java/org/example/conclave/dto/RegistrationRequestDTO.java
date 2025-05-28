package org.example.conclave.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


public class RegistrationRequestDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "RegistrationRequestDTO{username='" + username + "', password='" + password + "'}";
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
