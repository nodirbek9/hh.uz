package uz.java.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "role", ignore = true)
//    @Mapping(target = "keycloakId", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    User toEntity(UserRequest request);
}
