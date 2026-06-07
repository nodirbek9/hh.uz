package uz.java.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import uz.java.filter.GlobalFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final GlobalFilter gloabalFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthWhiteListProperty authWhiteListProperty;
    //    1. Basic authorization
//    2. Form-based authorization
//    3. JWT token orqali
//    4. Oauth 2 authorization
//    5. Keycloak orqali

    // JWT - JSON web token -->> bu backend va frontend cherez token orqali authorizatsiya qiladi
    // 1. Front login page ga login parol ni inputga yozib jonatadi
    // 2. Backend uni olib validatsiya ga tekshiradi bu jarayon GlobalFilter degan class da boladi
    // 3. Validatsiyaga tokenni issueAt ga backend tekshiradi yani muddati otib ketganmi yoki yoq
    // 4. validatsiyadan otgandan kn filter ni ozi controllerga yonaltirib yuboradi, agar otmasa Unauthorized qaytaramiz
    // 5 controller ga keganda agar controller ustida @PreAuthorize degan annotatsiya bolsa uni role ga tekshiradi
    // Agar role shu api ga dostupi bolsa api ga murojat qila oladi, dostup bolmasa Acess Denied qaytariladi

    // Token geneation
//    token login api da backend uni generatsiya qilib beradi qaysidir algoritm va secret_key orqali
//    uni muddatini ham backend chilar qoyadi, ex: 1 kun, 1 oy, 1 soat

//    Sessiya
    // Global filter da token orqali user malumotlari topilgandan keyin
//    uni sessiyaga saqlaymiz(user malumotlari va authorities --> role, permission)
//        va u security dagi SecurityContextHolder class ga set qilinadi

    //    Global filter bu har safar request shunga tushadi controller ga kelishidan oldin

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(authWhiteListProperty.getWhiteList().toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(gloabalFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:7070", "http://localhost:3001", "https://hh.uz"));
        config.addAllowedHeader("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    // basic, form-based auths uchun
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
