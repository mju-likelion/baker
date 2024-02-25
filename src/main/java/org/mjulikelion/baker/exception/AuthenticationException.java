package org.mjulikelion.baker.exception;

import org.mjulikelion.baker.errorcode.ErrorCode;

public class AuthenticationException extends CustomException {
    public AuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
