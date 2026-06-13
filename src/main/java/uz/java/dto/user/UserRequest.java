package uz.java.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    private Long roleId;
    private Long organizationId;
    private String photoUrl;
    private String phone;
}
