package org.mjulikelion.baker.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //서버 내부 오류들
    INTERNAL_SERVER_ERROR("5000", "알 수 없는 서버 내부 오류."),//서버 내부 오류
    //잘못된 경로, 메소드 오류들
    METHOD_NOT_ALLOWED_ERROR("4050", "허용되지 않은 메소드 입니다."),//허용되지 않은 메소드
    NO_RESOURCE_ERROR("4040", "해당 리소스를 찾을 수 없습니다."),//리소스를 찾을 수 없음(잘못된 URI)
    //잘못된 데이터 오류들
    INVALID_REQUEST_FORMAT_ERROR("4000", "유효하지 않은 Body 형식입니다."),//요청 형식이 잘못됨
    INVALID_PART_ERROR("4001", "유효하지 않은 파트입니다."),//유효하지 않은 파트
    INVALID_PAGE_ERROR("4002", "페이지가 유효하지 않습니다."),//페이지가 유효하지 않음
    PARAM_NOT_FOUND_ERROR("4003", "필수 파라미터가 누락되었습니다.");//필수 파라미터가 누락됨


    private final String code;
    private final String message;
}
