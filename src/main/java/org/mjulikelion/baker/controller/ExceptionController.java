package org.mjulikelion.baker.controller;

import static org.mjulikelion.baker.constant.EtcConstant.COLON;
import static org.mjulikelion.baker.dto.response.ResponseDto.getFromCustomException;

import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.baker.dto.response.ResponseDto;
import org.mjulikelion.baker.errorcode.ErrorCode;
import org.mjulikelion.baker.errorcode.ValidationErrorCode;
import org.mjulikelion.baker.exception.AuthenticationException;
import org.mjulikelion.baker.exception.InvalidDataException;
import org.mjulikelion.baker.exception.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //MissingServletRequestParameterException 예외를 처리하는 핸들러(필수 파라미터가 누락된 경우)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDto<Void>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException missingServletRequestParameterException) {
        log.error("MissingServletRequestParameterException: {}", missingServletRequestParameterException.getMessage());
        ErrorCode errorCode = ErrorCode.PARAM_NOT_FOUND_ERROR;
        String code = errorCode.getCode();
        String message = errorCode.getMessage() + COLON + missingServletRequestParameterException.getParameterName();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.BAD_REQUEST);
    }

    //MethodArgumentNotValidException 예외를 처리하는 핸들러(Body(dto)의 Validation에 실패한 경우)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("MethodArgumentNotValidException: {}", methodArgumentNotValidException.getMessage());
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError == null) {
            return new ResponseEntity<>(
                    ResponseDto.res(HttpStatus.BAD_REQUEST.toString(), methodArgumentNotValidException.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        ValidationErrorCode validationErrorCode = ValidationErrorCode.resolveAnnotation(fieldError.getCode());
        String code = validationErrorCode.getCode();
        String message = validationErrorCode.getMessage() + COLON + fieldError.getDefaultMessage();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.BAD_REQUEST);
    }

    //HandlerMethodValidationException 예외를 처리하는 핸들러(HandlerMethod의 Validation에 실패한 경우(controller의 메소드의 파라미터에 대한 Validation))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseDto<Void>> handleHandlerMethodValidationException(
            HandlerMethodValidationException handlerMethodValidationException) {
        log.error("HandlerMethodValidationException: {}", handlerMethodValidationException.getMessage());
        ValidationErrorCode errorCode = ValidationErrorCode.VALIDATION;
        String code = errorCode.getCode();
        String message = errorCode.getMessage() + COLON
                + handlerMethodValidationException.getDetailMessageArguments()[0].toString();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.BAD_REQUEST);
    }

    //HttpMessageNotReadableException 예외를 처리하는 핸들러(Body가 잘못된 경우(json 형식이 잘못된 경우))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<Void>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error("HttpMessageNotReadableException: {}", httpMessageNotReadableException.getMessage());
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_FORMAT_ERROR;
        String code = errorCode.getCode();
        String message = errorCode.getMessage() + COLON + httpMessageNotReadableException.getMessage();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.BAD_REQUEST);
    }

    //HttpRequestMethodNotSupportedException 예외를 처리하는 핸들러(요청의 메소드가 잘못된 경우)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDto<Void>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        log.error("HttpRequestMethodNotSupportedException: {}", httpRequestMethodNotSupportedException.getMessage());
        ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED_ERROR;
        String code = errorCode.getCode();
        String message = errorCode.getMessage();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.METHOD_NOT_ALLOWED);
    }

    //NoResourceFoundException 예외를 처리하는 핸들러(리소스를 찾을 수 없는 경우(URI가 잘못된 경우))
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseDto<Void>> handleNoResourceFoundException(
            NoResourceFoundException noResourceFoundException) {
        log.error("NoResourceFoundException: {}", noResourceFoundException.getMessage());
        ErrorCode errorCode = ErrorCode.NO_RESOURCE_ERROR;
        String code = errorCode.getCode();
        String message = errorCode.getMessage();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.NOT_FOUND);
    }

    //Exception 예외를 처리하는 핸들러(해당 클래스에 정의되지 않은 예외를 처리하는 핸들러)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleException(Exception exception) {
        log.error("InternalServerException: {}", exception.getMessage());
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        String code = errorCode.getCode();
        String message = errorCode.getMessage() + COLON + exception.getClass();
        return new ResponseEntity<>(ResponseDto.res(code, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // InvalidDataException 예외를 처리하는 핸들러(유효하지 않은 데이터가 요청된 경우)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseDto<Void>> handleInvalidDataException(InvalidDataException invalidDataException) {
        log.error("InvalidDataException: {}", invalidDataException.getMessage());
        ResponseDto<Void> responseDto = getFromCustomException(invalidDataException);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    // JwtException 예외를 처리하는 핸들러(Jwt 토큰이 유효하지 않은 경우)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseDto<Void>> handleJwtException(JwtException jwtException) {
        log.error("JwtException: {}", jwtException.getMessage());
        ResponseDto<Void> responseDto = getFromCustomException(jwtException);
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    // AuthenticationException 예외를 처리하는 핸들러(인증에 실패한 경우)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto<Void>> handleAuthenticationException(
            AuthenticationException authenticationException) {
        log.error("AuthenticationException: {}", authenticationException.getMessage());
        ResponseDto<Void> responseDto = getFromCustomException(authenticationException);
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }
}
