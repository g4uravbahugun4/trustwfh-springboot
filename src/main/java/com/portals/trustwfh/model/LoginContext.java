package com.portals.trustwfh.model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

public final class LoginContext {

    private final String userId;
    private final String ipAddress;
    private final String userAgent;
    private final String deviceToken;
    private final String companyDeviceCertId;
    private final String deviceFingerprint;
    private final Instant timestamp;

    private LoginContext(
            String userId,
            String ipAddress,
            String userAgent,
            String deviceToken,
            String companyDeviceCertId,
            String deviceFingerprint,
            Instant timestamp) {
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceToken = deviceToken;
        this.companyDeviceCertId = companyDeviceCertId;
        this.deviceFingerprint = deviceFingerprint;
        this.timestamp = timestamp;
    }

    public static LoginContext from(String userId, HttpServletRequest request) {
        String token = extractCookie(request, "trusted_device_id").orElse(null);
        String certId = request.getHeader("X-Client-Cert-Id");
        String userAgent = request.getHeader("User-Agent");
        String language = request.getHeader("Accept-Language");

        // Simple heuristic fingerprint: UA + Language
        // In production, this would be more complex or client-side generated
        String fingerprint = (userAgent != null ? userAgent : "") + "|" + (language != null ? language : "");
        String fingerprintHash = Integer.toHexString(fingerprint.hashCode());

        return new LoginContext(
                userId,
                request.getRemoteAddr(),
                userAgent,
                token,
                certId,
                fingerprintHash,
                Instant.now());
    }

    private static Optional<String> extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null)
            return Optional.empty();
        return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    public String getUserId() {
        return userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getCompanyDeviceCertId() {
        return companyDeviceCertId;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public boolean hasDeviceToken() {
        return deviceToken != null && !deviceToken.isEmpty();
    }

    public boolean hasClientCert() {
        return companyDeviceCertId != null && !companyDeviceCertId.isEmpty();
    }
}
