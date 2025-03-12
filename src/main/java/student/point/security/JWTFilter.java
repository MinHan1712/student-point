package student.point.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    public static final String WSO2_BACKEND_HEADER = "X-JWT-Assertion";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "X-Total-Count");
        String backendToken = resolveBackendToken(httpServletRequest);
        String gatewayToken = resolveGatewayToken(httpServletRequest);

        //        if (StringUtils.hasText(backendToken)) {
        //            Map<String, Object> claims = this.tokenProvider.validateToken(backendToken);
        //            if (claims.isEmpty()) {
        //                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        //                return;
        //            }
        //
        //            String user = this.tokenProvider.getSubject(claims);
        //            String method = httpServletRequest.getMethod();
        //            String path = httpServletRequest.getRequestURI();
        //
        //            EnforcePolicyRequest request = new EnforcePolicyRequest();
        //            request.setUser(user);
        //            request.setPath(path);
        //            request.setMethod(method);
        //
        //            Map<String, Object> response = internalFeignClient.enforcePolicy(request).getBody();
        //            if (response == null || response.get("code") == null) {
        //                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        //                return;
        //            }
        //
        //            Integer code = (Integer) response.get("code");
        //            if (code != HttpStatus.OK.value()) {
        //                httpServletResponse.setStatus(code);
        //                return;
        //            }
        //
        //            List<String> roles = response.get("roles") != null ? (List<String>) response.get("roles") : new ArrayList<>();
        //            Authentication authentication = this.tokenProvider.getAuthentication(claims, roles, backendToken, gatewayToken);
        //            SecurityContextHolder.getContext().setAuthentication(authentication);
        //
        //            filterChain.doFilter(servletRequest, servletResponse);
        //            return;
        //        }

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
