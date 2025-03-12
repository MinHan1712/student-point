package student.point.security;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import student.point.management.SecurityMetersService;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import static com.nimbusds.jose.JOSEObjectType.JWT;

@Component
public class TokenProvider {

    private final Logger LOG = LoggerFactory.getLogger(TokenProvider.class);

    private static final String JWT_SUBJECT_KEY = "sub";

    private final JwtDecoder jwtDecoder;

    public TokenProvider(SecurityMetersService metersService, RSAPublicKey rsaPublicKey) {
        this.jwtDecoder = this.jwtDecoder(metersService, rsaPublicKey);
    }

    public JwtDecoder jwtDecoder(SecurityMetersService metersService, RSAPublicKey rsaPublicKey) {
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withPublicKey(rsaPublicKey)
            .signatureAlgorithm(SignatureAlgorithm.RS256)
            .jwtProcessorCustomizer(p -> p.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier<>(JWT, new JOSEObjectType("at+jwt"))))
            .build();

        return token -> {
            try {
                return nimbusJwtDecoder.decode(token);
            } catch (Exception e) {
                if (e.getMessage().contains("Invalid signature")) {
                    metersService.trackTokenInvalidSignature();
                } else if (e.getMessage().contains("Jwt expired at")) {
                    metersService.trackTokenExpired();
                } else if (
                    e.getMessage().contains("Invalid JWT serialization") ||
                        e.getMessage().contains("Malformed token") ||
                        e.getMessage().contains("Invalid unsecured/JWS/JWE")
                ) {
                    metersService.trackTokenMalformed();
                } else {
                    LOG.error("Unknown JWT error {}", e.getMessage());
                }
                LOG.debug(e.getMessage());
                throw e;
            }
        };
    }

    public Authentication getAuthentication(Map<String, Object> claims, List<String> roles, String backendToken, String gatewayToken) {
        String user = getSubject(claims);
        Collection<? extends GrantedAuthority> authorities = roles
            .stream()
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        User principal = new User(user, "", authorities);
        JwtCredential credential = new JwtCredential(backendToken, gatewayToken, claims);
        return new UsernamePasswordAuthenticationToken(principal, credential, authorities);
    }

    public String getSubject(Map<String, Object> claims) {
        Object o = claims.get(JWT_SUBJECT_KEY);
        if (o == null) {
            throw new IllegalStateException("Jwt subject is NULL");
        }
        return o.toString();
    }

    public Map<String, Object> validateToken(String authToken) {
        try {
            return jwtDecoder.decode(authToken).getClaims();
        } catch (JwtException e) {
            return Collections.emptyMap();
        }
    }
}
