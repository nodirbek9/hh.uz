package uz.java.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import uz.java.config.AuthWhiteListProperty;
import uz.java.config.CustomUserDetails;
import uz.java.exception.GenericRuntimeException;
import uz.java.exception.InvalidDataException;
import uz.java.service.CustomUserDetailService;
import uz.java.service.JwtTokenService;
import uz.java.util.ApiConstants;

import java.io.IOException;

@Slf4j
@Component // -->> generic streotype anotation
@RequiredArgsConstructor
public class GlobalFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final CustomUserDetailService userDetailsService;
    private final AuthWhiteListProperty authWhiteListProperty;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis(); // hosirgi vaqtni long tipida oladi
        String requestUri = request.getRequestURI();
        log.info("getting request URI: " + requestUri);
        log.info("isOpenPath: {}", isOpenPath(requestUri));
        if (!isOpenPath(requestUri)) {
            try {
                log.info("Attempting to extract token from request");
                String token = getTokenFromRequest(request);
                log.info("Token extracted: {}", token != null ? "YES (length: " + token.length() + ")" : "NO");
                DecodedJWT verified = jwtTokenService.validate(token);
                String keycloakId = verified.getClaim("sub").asString();
                CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(keycloakId);
                authenticate(request, customUserDetails);
            } catch (GenericRuntimeException | AccessDeniedException | InvalidDataException e) {
                log.error("Global filter error: {}", e.getMessage(), e);
                resolver.resolveException(request, response, null, e);
                return;
            }
        } else {
            log.info("Path is in whitelist, skipping authentication");
        }
        setLang(request, response);
        filterChain.doFilter(request, response);
        long finish = System.currentTimeMillis();
        log.info("->->Request = [ {}?{} ] Elapsed time to proceed this request = {}", request.getRequestURI(),
                request.getQueryString() == null ? "" : request.getQueryString(), finish - start);
    }

    private void authenticate(HttpServletRequest request, CustomUserDetails userDetails) {
        // user malumotlari va authoritylari(role, permission) sessiyaga saqlash joyi
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(ApiConstants.HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            throw new InvalidDataException("token.is.null");
        }
    }

    private boolean isOpenPath(String currentPath) {
        return authWhiteListProperty.getWhiteList().stream()
                .anyMatch(currentPath::contains);
    }

    private void setLang(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(ApiConstants.LANG);
//        localeResolver.setLocale(request, response, new Locale(Objects.requireNonNullElse(header, ApiConstants.DEFAULT_LANG)));
    }
}
