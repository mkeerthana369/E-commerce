package com.incture.e_commerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDtoRequest {
    
    @NotBlank(message = "Name is required")
    private String user_name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String user_email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String user_password;
    
    @NotBlank(message = "Role is required")
    private String user_role;

    public String getUser_name() {
        return user_name;
    }
    
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    public String getUser_email() {
        return user_email;
    }
    
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    
    public String getUser_password() {
        return user_password;
    }
    
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    
    public String getUser_role() {
        return user_role;
    }
    
    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}