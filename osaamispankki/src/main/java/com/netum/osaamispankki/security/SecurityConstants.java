package com.netum.osaamispankki.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/user/**";
    public static final String PHOTO_URLS = "/api/photo/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecurityKeyToGenerateTokens";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_OF_JWT = "authorization";
    public static final Long EXPIRATION_TIME = 3000_000L;

}
