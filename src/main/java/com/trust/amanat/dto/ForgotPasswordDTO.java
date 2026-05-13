package com.trust.amanat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 12, message = "Username must be between 6 and 12 characters")
    private String userName;


    @NotBlank(message = "New Password is required")
    @Size(min = 8, max = 24,
            message = "Password must be between 8 and 24 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,24}$",
            message = "Password must contain uppercase, lowercase, number and special character")
    private String newPassword;

    private String password;
    private String confirmPassword;
}
