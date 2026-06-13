package uz.java.client;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import uz.java.dto.auth.TokenResponse;

public interface KeycloakServiceClient {
    @FormUrlEncoded
    @POST("/realms/hh-web-app-realm/protocol/openid-connect/token")
    Call<TokenResponse> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("username") String username,
            @Field("password") String password
    );
}
