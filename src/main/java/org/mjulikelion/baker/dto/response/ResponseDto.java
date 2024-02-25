package org.mjulikelion.baker.dto.response;

import static org.mjulikelion.baker.constant.EtcConstant.COLON;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.mjulikelion.baker.exception.CustomException;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    @JsonProperty
    private final String statusCode;
    @JsonProperty
    private final String message;
    @JsonProperty
    private final T data;

    public ResponseDto(final String statusCode, final String resultMsg) {
        this.statusCode = statusCode;
        this.message = resultMsg;
        this.data = null;
    }

    public static <T> ResponseDto<T> res(final String statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static <T> ResponseDto<T> res(final String statusCode, final String resultMsg, final T data) {
        return ResponseDto.<T>builder()
                .data(data)
                .statusCode(statusCode)
                .message(resultMsg)
                .build();
    }

    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String resultMsg, final T data) {
        return ResponseDto.<T>builder()
                .data(data)
                .statusCode(String.valueOf(statusCode.value()))
                .message(resultMsg)
                .build();
    }

    public static ResponseDto<Void> getFromCustomException(CustomException e) {
        if (e.getMessage() == null) {
            return ResponseDto.res(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        }
        return ResponseDto.res(e.getErrorCode().getCode(),
                e.getErrorCode().getMessage() + COLON + e.getMessage());
    }
}
