package uz.java.mapper;

import org.mapstruct.*;
import uz.java.dto.user.UserProfileRequest;
import uz.java.dto.user.UserProfileResponse;
import uz.java.entity.user.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(source = "user.id",        target = "user.userId")
    @Mapping(source = "firstName",      target = "user.firstName")
    @Mapping(source = "lastName",       target = "user.lastName")
    @Mapping(source = "city",           target = "user.city")
    @Mapping(source = "country",        target = "user.country")
    @Mapping(source = "avatar",         target = "user.avatar")
    @Mapping(target = "educations",     ignore = true)
    @Mapping(target = "workExperiences",ignore = true)
    @Mapping(target = "languageSet",    ignore = true)
    UserProfileResponse toResponse(UserProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(UserProfileRequest request, @MappingTarget UserProfile profile);
}
