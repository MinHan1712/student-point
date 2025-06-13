package student.point.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import student.point.config.ApplicationProperties;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    public static final String WSO2_BACKEND_HEADER = "X-JWT-Assertion";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String API_KEY = "Authorization";
    private final TokenProvider tokenProvider;
    private final ApplicationProperties applicationProperties;

    public JWTFilter(TokenProvider tokenProvider, ApplicationProperties applicationProperties) {
        this.tokenProvider = tokenProvider;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "X-Total-Count");
        String backendToken = resolveBackendToken(httpServletRequest);
        String gatewayToken = resolveGatewayToken(httpServletRequest);
        String path = httpServletRequest.getRequestURI();
        if (path.contains("api/login")) {
            Authentication authentication =
                this.tokenProvider.getAuthentication(new HashMap<>(), Arrays.asList("ADMIN"), backendToken, gatewayToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String apiKey = httpServletRequest.getHeader(API_KEY);
        if (org.apache.commons.lang3.StringUtils.isBlank(apiKey) || !applicationProperties.getApiKey().equals(apiKey)) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        Authentication authentication =
            this.tokenProvider.getAuthentication(new HashMap<>(), Arrays.asList("ADMIN"), backendToken, gatewayToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveBackendToken(HttpServletRequest request) {
        String token = request.getHeader(WSO2_BACKEND_HEADER);
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }

    private String resolveGatewayToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
