package com.judeochalifu.auth.jwtauthenticationboilerplate.constants;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SIGN_UP_URL = "/account/sign-up";
    
}
