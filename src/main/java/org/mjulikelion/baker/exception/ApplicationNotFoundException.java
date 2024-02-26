package org.mjulikelion.baker.exception;

import org.mjulikelion.baker.errorcode.ErrorCode;

public class ApplicationNotFoundException extends CustomException {
    public ApplicationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
