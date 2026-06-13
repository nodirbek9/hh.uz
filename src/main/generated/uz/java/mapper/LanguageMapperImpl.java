package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.user.LanguageResponse;
import uz.java.dto.user.UserProfileRequest;
import uz.java.entity.enums.LanguageLevel;
import uz.java.entity.jobseeker.Language;
import uz.java.entity.user.UserProfile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class LanguageMapperImpl implements LanguageMapper {

    @Override
    public LanguageResponse toResponse(Language language) {
        if ( language == null ) {
            return null;
        }

        LanguageResponse languageResponse = new LanguageResponse();

        languageResponse.setUserProfileId( languageUserProfileId( language ) );
        languageResponse.setName( language.getName() );
        if ( language.getLevel() != null ) {
            languageResponse.setLevel( language.getLevel().name() );
        }

        return languageResponse;
    }

    @Override
    public Language toEntity(UserProfileRequest.LanguageDto languageDto) {
        if ( languageDto == null ) {
            return null;
        }

        Language language = new Language();

        language.setId( languageDto.getId() );
        language.setName( languageDto.getName() );
        if ( languageDto.getLevel() != null ) {
            language.setLevel( Enum.valueOf( LanguageLevel.class, languageDto.getLevel() ) );
        }

        return language;
    }

    private Long languageUserProfileId(Language language) {
        if ( language == null ) {
            return null;
        }
        UserProfile userProfile = language.getUserProfile();
        if ( userProfile == null ) {
            return null;
        }
        Long id = userProfile.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
