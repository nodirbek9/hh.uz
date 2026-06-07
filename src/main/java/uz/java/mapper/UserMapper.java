package uz.java.mapper;

import org.mapstruct.Mapper;
import uz.java.dto.auth.RegisterRequest;
import uz.java.entity.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);
}
