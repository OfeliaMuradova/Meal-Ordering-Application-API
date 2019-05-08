package com.development.ordering.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Constants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 24*60*60;
    public static final String SIGNING_KEY = "fsdfsdfds89fds98fd98f7d9s7f98ds7f9dsf79s7";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "schmoppes";
    public static final Authentication CURRENT_AUTH = SecurityContextHolder.getContext().getAuthentication();
}
