package uz.java.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.java.dto.auth.RegisterRequest;
import uz.java.entity.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-10T21:35:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {


    public User toUser(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( request.getUsername() );
        user.setEmail( request.getEmail() );
        user.setPassword( request.getPassword() );
        user.setRole( request.getRole() );

        return user;
    }
}
