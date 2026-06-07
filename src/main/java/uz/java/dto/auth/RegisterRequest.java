package uz.java.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import uz.java.entity.enums.UserRole;

@Data
public class RegisterRequest {

    @NotBlank(message = "username.must.not.be.blank")
    private String username;

    @NotBlank(message = "email.must.not.be.blank")
    @Email(message = "email.invalid")
    private String email;

    @NotBlank(message = "password.must.not.be.blank")
    @Size(min = 6, message = "password.too.short")
    private String password;

    @NotNull(message = "role.must.not.be.null")
    private UserRole role;  // JOB_SEEKER yoki EMPLOYER

    private String firstName;
    private String lastName;
}
