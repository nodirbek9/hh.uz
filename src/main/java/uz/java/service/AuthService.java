package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.config.CustomAuthenticationProvider;
import uz.java.config.CustomUserDetails;
import uz.java.dto.auth.RegisterRequest;
import uz.java.dto.auth.TokenResponse;
import uz.java.entity.enums.SessionUserStatus;
import uz.java.entity.user.SessionUser;
import uz.java.entity.user.User;
import uz.java.entity.user.UserProfile;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.InvalidDataException;
import uz.java.mapper.UserMapper;
import uz.java.repository.SessionUserRepository;
import uz.java.repository.UserProfileRepository;
import uz.java.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomAuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final SessionUserRepository sessionUserRepository;
    private final JwtTokenService jwtTokenService;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public TokenResponse login(String username, String password) {
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
        User user = userRepository.findById(userDetails.getUserId()).orElse(null);
        if (user == null)
            throw new GenericNotFoundException("user.not.found");

        SessionUser sessionUser = sessionUserRepository.findByUserId(user.getId());
        if (sessionUser != null) {
            String accessToken = jwtTokenService.generateToken(userDetails.getUsername());
            String refreshToken = jwtTokenService.generateRefreshToken(userDetails.getUsername());
            sessionUser.setAccessToken(accessToken);
            sessionUser.setRefreshToken(refreshToken);
            sessionUser.setStatus(SessionUserStatus.ACTIVE);
            sessionUserRepository.save(sessionUser);
            return TokenResponse.builder()
                    .accessToken(sessionUser.getAccessToken())
                    .refreshToken(sessionUser.getRefreshToken())
                    .build();
        }
        String accessToken = jwtTokenService.generateToken(userDetails.getUsername());
        String refreshToken = jwtTokenService.generateRefreshToken(userDetails.getUsername());
        sessionUserRepository.save(SessionUser.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(SessionUserStatus.ACTIVE)
                .build());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new InvalidDataException("username.already.exists");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new InvalidDataException("email.already.exists");

        // 2. User yaratish
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setIsActive(true);
        user.setIsVerified(false);
        User savedUser = userRepository.save(user);

        // 3. UserProfile avtomatik yaratish
        UserProfile profile = new UserProfile();
        profile.setUser(savedUser);
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        userProfileRepository.save(profile);

        // 4. Token generatsiya
        String accessToken  = jwtTokenService.generateToken(savedUser.getUsername());
        String refreshToken = jwtTokenService.generateRefreshToken(savedUser.getUsername());
        sessionUserRepository.save(SessionUser.builder()
                .user(savedUser)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(SessionUserStatus.ACTIVE)
                .build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        if (!jwtTokenService.isValid(refreshToken))
            throw new InvalidDataException("invalid.token");

        String username = jwtTokenService.subject(refreshToken);
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new GenericNotFoundException("user.not.found");

        SessionUser sessionUser = sessionUserRepository.findByUserId(user.getId());
        if (sessionUser == null || !sessionUser.getRefreshToken().equals(refreshToken))
            throw new InvalidDataException("invalid.token");

        String newAccessToken = jwtTokenService.generateToken(username);
        String newRefreshToken = jwtTokenService.generateRefreshToken(username);
        sessionUser.setAccessToken(newAccessToken);
        sessionUser.setRefreshToken(newRefreshToken);
        sessionUserRepository.save(sessionUser);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Transactional
    public Boolean logout(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new GenericNotFoundException("user.not.found");

        SessionUser sessionUser = sessionUserRepository.findByUserId(user.getId());
        if (sessionUser != null) {
            sessionUser.setStatus(SessionUserStatus.INACTIVE);
            sessionUserRepository.save(sessionUser);
        }
        return true;
    }

}
