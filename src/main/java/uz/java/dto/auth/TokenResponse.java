package uz.java.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("expiresIn")
    int expiresIn;
    @JsonProperty("refresh_expires_in")
    int refreshExpiresIn;
    @JsonProperty("token_type")
    String tokenType;
    String scope;
}
