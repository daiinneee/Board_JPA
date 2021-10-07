package com.study.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* Custom 예외 처리용 Exception 클래스 생성하기 */

// 개발자가 ErrorCode에 직접 정의한 Custom 예외를 처리할 Exception 클래스
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

	// ErrorResponse와 마찬가지로 ErrorCode를 통한 객체 생성만을 허용
    private final ErrorCode errorCode;

}