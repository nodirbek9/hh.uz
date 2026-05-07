package uz.java.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.java.dto.user.UserProfileRequest;
import uz.java.dto.user.UserProfileResponse;
import uz.java.entity.user.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileResponse toResponse(UserProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(UserProfileRequest request, @MappingTarget UserProfile profile);
}
