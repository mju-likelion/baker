package org.mjulikelion.baker.exception;

import org.mjulikelion.baker.errorcode.ErrorCode;

public class JwtException extends CustomException {
    public JwtException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
