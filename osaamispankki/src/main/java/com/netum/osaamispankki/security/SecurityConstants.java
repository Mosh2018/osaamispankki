package com.netum.osaamispankki.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/user/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecurityKeyToGenerateTokens";
    public static final String TOKEN_PERFIX = "Bearer ";
    public static final String HEADER_OF_JWT = "authorization";
    public static final Long EXPIRATION_TIME = 300_000L;

}
