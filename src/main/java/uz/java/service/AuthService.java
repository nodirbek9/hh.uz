package uz.java.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uz.java.client.KeycloakServiceClient;
import uz.java.config.CustomAuthenticationProvider;
import uz.java.dto.auth.RegisterRequest;
import uz.java.dto.auth.TokenResponse;
import uz.java.entity.user.User;
import uz.java.entity.user.UserProfile;
import uz.java.exception.GenericRuntimeException;
import uz.java.repository.UserProfileRepository;
import uz.java.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {
    private final KeycloakServiceClient keycloakServiceClient;
    private final Keycloak keycloak;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    UserProfileRepository userProfileRepository;

    @Value("${app.keycloak.client-id}")
    private String clientId;

    @Value("${app.keycloak.client-secret}")
    private String clientSecret;

    @Value("${app.keycloak.realm}")
    private String realm;

    public AuthService(@Value("${app.keycloak.keycloak-server-url}") String baseUrl,
                       JwtTokenService jwtTokenService,                           // ← добавить
                       Keycloak keycloakAdminClient,
                       PasswordEncoder passwordEncoder,
                       UserProfileRepository userProfileRepository,
                       CustomAuthenticationProvider customAuthenticationProvider, UserRepository userRepository) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtTokenService = jwtTokenService;
        this.keycloak = keycloakAdminClient;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.keycloakServiceClient = retrofit.create(KeycloakServiceClient.class);
    }

    public TokenResponse login(String username, String password) {
        try {
            Response<TokenResponse> response = keycloakServiceClient.getToken(
                    "password",
                    clientId,
                    clientSecret,
                    username,
                    password
            ).execute();
            String errorBody = "";
            if (!response.isSuccessful()) {
                try {
                    if (response.errorBody() != null) {
                        errorBody = response.errorBody().string();
                        throw new BadRequestException("Token olishda xatolik: " + errorBody);
                    }
                } catch (IOException e) {
                    throw new BadRequestException("Xatolikni o'qishda muammo yuz berdi.");
                }
            }
            if (response.body() == null) {
                throw new BadRequestException("Token olishda xatolik : response.body() == null" + "\n" + response.message() + "\n" + " --- getPinfl");
            }
            if (response.body().getAccessToken() == null) {
                throw new BadRequestException("Token olishda xatolik : response.body().getAccessToken() == null" + "\n" + response.message() + "\n" + " --- getPinfl");
            }
            DecodedJWT data = JWT.decode(response.body().getAccessToken());

            customAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                    data.getClaim("sub").asString(), password
            ));
            return TokenResponse.builder()
                    .accessToken(response.body().getAccessToken())
                    .refreshToken(response.body().getRefreshToken())
                    .build();
        } catch (IOException e) {
            throw new GenericRuntimeException("Token olishda xatolik: " + e.getMessage());
        }
    }

    public Long register(RegisterRequest request) {
        // 1. Проверка username в Keycloak
        if (!keycloak.realm(realm).users()
                .searchByUsername(request.getUsername(), false).isEmpty()) {
            throw new GenericRuntimeException("username.already.exists");
        }

        // 2. Проверить локально тоже
        if (userRepository.existsByUsername(request.getUsername()))
            throw new GenericRuntimeException("username.already.exists");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new GenericRuntimeException("email.already.exists");

        // 3. Создать Keycloak пользователя
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(request.getUsername());
        kcUser.setEmail(request.getEmail());
        kcUser.setFirstName(request.getFirstName());
        kcUser.setLastName(request.getLastName());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(request.getPassword());
        cred.setTemporary(false);
        kcUser.setCredentials(List.of(cred));

        String keycloakUserId = null;
        try (var response = keycloak.realm(realm).users().create(kcUser)) {

            if (response.getStatusInfo().getFamily() != jakarta.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
                throw new GenericRuntimeException("keycloak.user.create.failed");
            }

            // Извлечь keycloakUserId из Location (как у учителя)
            String location = response.getLocation().toString();
            keycloakUserId = location.substring(location.lastIndexOf("/") + 1);

            // 4. Сохранить в БД (как saveEntity у учителя, но вручную)
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            user.setIsActive(true);
            user.setKeycloakId(UUID.fromString(keycloakUserId));  // ← ВАЖНО!
            userRepository.save(user);

            // 5. UserProfile
            UserProfile profile = new UserProfile();
            profile.setUser(user);
            profile.setFirstName(request.getFirstName());
            profile.setLastName(request.getLastName());

            // 6. Вернуть токен
            return userProfileRepository.save(profile).getId();

        } catch (Exception e) {
            // Rollback (как у учителя)
            if (keycloakUserId != null) {
                try {
                    keycloak.realm(realm).users().delete(keycloakUserId);
                } catch (Exception ex) {
                    log.error("Failed to rollback Keycloak user: {}", keycloakUserId, ex);
                }
            }
            throw new GenericRuntimeException(e.getMessage());
        }
    }
    private boolean checkUsername(String username) {
        return keycloak.realm(realm)
                .users()
                .searchByUsername(username, false).isEmpty();
    }

}
