package uz.java.mapper;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.user.UserProfileRequest;
import uz.java.dto.user.UserProfileResponse;
import uz.java.dto.user.UserResponse;
import uz.java.entity.enums.Gender;
import uz.java.entity.enums.LanguageLevel;
import uz.java.entity.jobseeker.Language;
import uz.java.entity.user.User;
import uz.java.entity.user.UserProfile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfileResponse toResponse(UserProfile profile) {
        if ( profile == null ) {
            return null;
        }

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        if ( profile.getUser() != null ) {
            if ( userProfileResponse.getUser() == null ) {
                userProfileResponse.setUser( new UserResponse() );
            }
            userToUserResponse( profile.getUser(), userProfileResponse.getUser() );
        }
        if ( userProfileResponse.getUser() == null ) {
            userProfileResponse.setUser( new UserResponse() );
        }
        userProfileToUserResponse( profile, userProfileResponse.getUser() );

        return userProfileResponse;
    }

    @Override
    public void updateFromRequest(UserProfileRequest request, UserProfile profile) {
        if ( request == null ) {
            return;
        }

        if ( request.getAvatar() != null ) {
            profile.setAvatar( request.getAvatar() );
        }
        if ( request.getFirstName() != null ) {
            profile.setFirstName( request.getFirstName() );
        }
        if ( request.getLastName() != null ) {
            profile.setLastName( request.getLastName() );
        }
        if ( request.getBirthDate() != null ) {
            profile.setBirthDate( LocalDate.parse( request.getBirthDate() ) );
        }
        if ( request.getGender() != null ) {
            profile.setGender( Enum.valueOf( Gender.class, request.getGender() ) );
        }
        if ( request.getCity() != null ) {
            profile.setCity( request.getCity() );
        }
        if ( request.getCountry() != null ) {
            profile.setCountry( request.getCountry() );
        }
        if ( profile.getLanguages() != null ) {
            Set<Language> set = languageDtoSetToLanguageSet( request.getLanguages() );
            if ( set != null ) {
                profile.getLanguages().clear();
                profile.getLanguages().addAll( set );
            }
        }
        else {
            Set<Language> set = languageDtoSetToLanguageSet( request.getLanguages() );
            if ( set != null ) {
                profile.setLanguages( set );
            }
        }
    }

    protected void userToUserResponse(User user, UserResponse mappingTarget) {
        if ( user == null ) {
            return;
        }

        mappingTarget.setUserId( user.getId() );
    }

    protected void userProfileToUserResponse(UserProfile userProfile, UserResponse mappingTarget) {
        if ( userProfile == null ) {
            return;
        }

        mappingTarget.setFirstName( userProfile.getFirstName() );
        mappingTarget.setLastName( userProfile.getLastName() );
        mappingTarget.setCity( userProfile.getCity() );
        mappingTarget.setCountry( userProfile.getCountry() );
        mappingTarget.setAvatar( userProfile.getAvatar() );
    }

    protected Language languageDtoToLanguage(UserProfileRequest.LanguageDto languageDto) {
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

    protected Set<Language> languageDtoSetToLanguageSet(Set<UserProfileRequest.LanguageDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Language> set1 = new LinkedHashSet<Language>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UserProfileRequest.LanguageDto languageDto : set ) {
            set1.add( languageDtoToLanguage( languageDto ) );
        }

        return set1;
    }
}
