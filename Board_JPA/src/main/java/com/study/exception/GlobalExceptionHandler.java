package com.study.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/* 전역 예외 핸들링용 GlobalExceptionHandler 생성하기 */

/* 스프링은 예외 처리를 위해 @ControllerAdvice와 @ExceptionHandler 등의 기능을 지원
 * @ControllerAdvice : 컨트롤러 전역에서 발생할 수 있는 예외를 잡아 Throw 해줌
 * @ExcptionHandler : 특정 클래스에서 발생할 수 있는 예외를 잡아 Throw 해줌
 * 일반적으로 @ExceptionHandler는 @ControllerAdvice가 선언된 클래스에 포함된 메소드에 선언
 * 
 * 지금은 페이지에 대한 예외 처리는 의미가 X -> @RestControllerAdvice를 선언
 * @RestControllerAdvice : @ControllerAdvice + @ResponseBody
 * 
 * @Slf4j : 롬복에서 제공해주는 기능
 * 		    해당 어노테이션이 선언된 클래스에 자동으로 로그 객체 생성
 * 	        log.error(), log.debug()와 같이 로깅 관련 메소드를 사용 가능
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
     * Developer Custom Exception
     */
	// ResponseEntity<ErrorResponse>
	// ResponseEntity<T>는 HTTP Request에 대한 응답 데이터를 포함하는 클래스
	// <Type>에 해당하는 데이터와 HTTP 상태 코드를 함께 리턴 가능
	// 예외가 발생했을 때, ErrorResponse 형식으로 예외 정보를 Response로 내려주게 됨
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("handleCustomException: {}", e.getErrorCode());
        return ResponseEntity
                .status(e.getErrorCode().getStatus().value())
                .body(new ErrorResponse(e.getErrorCode()));
    }

    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED));
    }

    /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}