package org.mjulikelion.baker.util.security;

import static org.mjulikelion.baker.errorcode.ErrorCode.AUTHENTICATION_NOT_FOUND_ERROR;

import org.mjulikelion.baker.exception.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getCurrentManagerId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new AuthenticationException(AUTHENTICATION_NOT_FOUND_ERROR, "인증 정보를 찾을 수 없습니다.");
        }
        return authentication.getName();
    }
}
