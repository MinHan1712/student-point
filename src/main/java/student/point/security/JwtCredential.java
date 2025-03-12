package student.point.security;

import java.util.Map;

public record JwtCredential(String backendToken, String gatewayToken, Map<String, Object> claims) {
}
