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
    APPLICATION_NOT_FOUND_ERROR("4041", "해당 지원서가 존재하지 않습니다."),//지원서가 존재하지 않음
    APPLICATION_INTRODUCE_NOT_FOUND_ERROR("4042", "해당 지원서에 자기소개가 존재하지 않습니다."),//자기소개가 존재하지 않음
    //잘못된 데이터 오류들
    INVALID_REQUEST_FORMAT_ERROR("4000", "유효하지 않은 Body 형식입니다."),//요청 형식이 잘못됨
    INVALID_PART_ERROR("4001", "유효하지 않은 파트입니다."),//유효하지 않은 파트
    INVALID_PAGE_ERROR("4002", "페이지가 유효하지 않습니다."),//페이지가 유효하지 않음
    PARAM_NOT_FOUND_ERROR("4003", "필수 파라미터가 누락되었습니다."),//필수 파라미터가 누락됨
    APPLICATION_ALREADY_PASS_ERROR("4004", "이미 합격한 지원서입니다."),//이미 합격한 지원서
    APPLICATION_ALREADY_REJECT_ERROR("4005", "이미 불합격한 지원서입니다."),//이미 불합격한 지원서
    //인증/권한 오류들
    AUTHENTICATION_ERROR("4010", "인증 오류입니다."),//인증 오류
    AUTHENTICATION_NOT_FOUND_ERROR("4011", "인증 정보를 찾을 수 없습니다."),//인증 정보를 찾을 수 없음
    AUTHENTICATION_REQUIRED_ERROR("4012", "로그인되어있지 않습니다"),//권한이 없음
    ACCESS_DENIED_ERROR("4031", "접근이 거부되었습니다."),//접근이 거부됨
    //JWT 토큰 에러
    JWT_TOKEN_ERROR("8000", "JWT 권한 정보 검증 오류."),//JWT 토큰 에러
    JWT_TOKEN_PROVIDER_ERROR("8001", "JWT 토큰 제공자 오류."),//JWT 토큰 제공자 에러
    JWT_INVALID_TOKEN_ERROR("8002", "유효하지 않은 JWT 토큰입니다."),//유효하지 않은 JWT 토큰
    JWT_EXPIRED_TOKEN_ERROR("8003", "만료된 JWT 토큰입니다."),//만료된 JWT 토큰
    JWT_UNSUPPORTED_TOKEN_ERROR("8004", "지원되지 않는 JWT 토큰입니다."),//지원되지 않는 JWT 토큰
    JWT_CLAIMS_STRING_ERROR("8005", "JWT 클레임 문자열이 비어있습니다."),//JWT 클레임 문자열이 비어있음
    JWT_UNKNOWN_ERROR("8006", "알 수 없는 JWT 토큰 에러입니다."),//알 수 없는 JWT 토큰 에러
    JWT_NOT_FOUND_ERROR("8007", "JWT 토큰을 찾을 수 없습니다.");//JWT 토큰을 찾을 수 없음

    private final String code;
    private final String message;
}
