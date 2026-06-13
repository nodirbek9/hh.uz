package uz.java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.config.CustomUserDetails;
import uz.java.entity.user.User;
import uz.java.exception.GenericNotFoundException;
import uz.java.repository.UserRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    private final static Function<String, SimpleGrantedAuthority> authority = SimpleGrantedAuthority::new;

    @Override
    @Transactional(readOnly = true)
    public CustomUserDetails loadUserByUsername(String keycloakId) throws UsernameNotFoundException {
            User user = userRepository.findByKeycloakId(UUID.fromString(keycloakId)).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with id: " + keycloakId));

            Set<GrantedAuthority> authorities = new HashSet<>();
            try {
                if (Objects.nonNull(user.getRole()))
                    authorities.add(authority.apply(user.getRole().getAuthority()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return new CustomUserDetails(user, authorities);
        }
}
