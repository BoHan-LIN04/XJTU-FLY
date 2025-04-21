package org.flight_helper.flight_helper_bigdata.common;

import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // 获取错误码
    private final String errorCode;  // 错误码
    // 获取错误信息
    private final String errorMessage; // 错误信息

    // 构造方法
    public CustomException(String errorCode, String errorMessage) {
        super(errorMessage);  // 将错误信息传递给 RuntimeException
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    // 可选构造方法，带有Throwable cause（用于链式异常）
    public CustomException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);  // 将错误信息和异常链传递给 RuntimeException
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
