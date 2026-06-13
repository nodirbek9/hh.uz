package uz.java.config;

import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.java.service.CustomUserDetailService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, CustomUserDetailService customUserDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String keycloakId = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(keycloakId);
        if (!passwordEncoder.matches(password, userDetails.getUser().getPassword()))
            throw new ValidationException("invalid.user.details");
        return new UsernamePasswordAuthenticationToken(userDetails, authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
