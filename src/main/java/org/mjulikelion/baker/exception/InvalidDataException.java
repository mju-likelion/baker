package org.mjulikelion.baker.exception;

import lombok.Getter;
import org.mjulikelion.baker.errorcode.ErrorCode;

@Getter
public class InvalidDataException extends CustomException {
    public InvalidDataException(ErrorCode errorCode) {
        super(errorCode);
    }
}
