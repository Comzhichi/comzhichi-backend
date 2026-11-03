package com.comzhichi.config;

import com.comzhichi.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class CurrentUser {

    private CurrentUser() {
    }

    public static UserPrincipal getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return null;
        }
        return principal;
    }

    public static Long getUserId() {
        UserPrincipal principal = getPrincipal();
        return (principal == null) ? null : principal.getUser().getId();
    }
}