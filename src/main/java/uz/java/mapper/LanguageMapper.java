package uz.java.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.java.dto.user.LanguageResponse;
import uz.java.dto.user.UserProfileRequest;
import uz.java.entity.jobseeker.Language;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    LanguageResponse toResponse(Language language);

    @Mapping(target = "userProfile", ignore = true)
    Language toEntity(UserProfileRequest.LanguageDto languageDto);
}
