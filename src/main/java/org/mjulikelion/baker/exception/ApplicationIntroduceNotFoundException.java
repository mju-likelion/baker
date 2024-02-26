package org.mjulikelion.baker.exception;

import org.mjulikelion.baker.errorcode.ErrorCode;

public class ApplicationIntroduceNotFoundException extends CustomException {
    public ApplicationIntroduceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
