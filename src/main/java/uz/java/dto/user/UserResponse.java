package uz.java.dto.user;

import lombok.Data;

@Data
public class UserResponse {

    private Long userId;

    private String avatar;

    private String firstName;

    private String lastName;

    private String birthDate;

    private String city;

    private String country;


}
