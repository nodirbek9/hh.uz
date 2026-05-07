package uz.java.dto.user;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserProfileRequest {

    private Long userId;

    private String avatar;

    private String firstName;

    private String lastName;

    private String birthDate;

    private String gender;

    private String city;

    private String country;

    private Set<LanguageDto> languages = new HashSet<>();

    @Data
    public static class LanguageDto {
        private Long id;
        private String name;
        private String level;
    }

}