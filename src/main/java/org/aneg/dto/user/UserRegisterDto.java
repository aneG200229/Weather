package org.aneg.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "Login cannot be empty")
    @Size(min = 3, message = "Login is longer than 3 characters")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password is longer than 6 characters")
    private String password;
    @NotBlank
    private String repeatPassword;
}
