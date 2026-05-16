package com.example.flyaway.auth;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JwtService {
    private static final String SECRET = "mi_secreto_super_seguro";
    private static final Pattern SUB_PATTERN = Pattern.compile("\"sub\"\\s*:\\s*\"(\\d+)\"");
    private static final Pattern EXP_PATTERN = Pattern.compile("\"exp\"\\s*:\\s*(\\d+)");

    public String createToken(Long userId, String email) {
        try {
            long now = Instant.now().getEpochSecond();
            String header = toBase64Url("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
            String payloadJson = "{\"sub\":\"%s\",\"email\":\"%s\",\"iat\":%d,\"exp\":%d}"
                    .formatted(userId, escapeJson(email), now, now + 3600);
            String payload = toBase64Url(payloadJson.getBytes(StandardCharsets.UTF_8));
            String unsigned = header + "." + payload;
            return unsigned + "." + sign(unsigned);
        } catch (Exception e) {
            throw new IllegalStateException("Could not create token", e);
        }
    }

    public Long validateAndGetUserId(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            String unsigned = parts[0] + "." + parts[1];
            if (!sign(unsigned).equals(parts[2])) {
                return null;
            }
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            Matcher exp = EXP_PATTERN.matcher(payload);
            if (!exp.find() || Long.parseLong(exp.group(1)) < Instant.now().getEpochSecond()) {
                return null;
            }
            Matcher sub = SUB_PATTERN.matcher(payload);
            return sub.find() ? Long.valueOf(sub.group(1)) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String sign(String content) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return toBase64Url(mac.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }

    private String toBase64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
