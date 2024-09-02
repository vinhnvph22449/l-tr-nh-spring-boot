package com.Dormitory.Exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999,"UNCATEGORIZED ERROR",HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(102,"User Existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(103,"USERNAME_INVALIDD",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(104, "PASSWORD > 8 KI TU",HttpStatus.BAD_REQUEST),
    USER_Not_EXISTED(105,"User Not Existed",HttpStatus.NOT_FOUND),
    UANTHENTICATED(106,"UANTHENTICATED",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(107,"You do not have permission",HttpStatus.FORBIDDEN),

    ;

    ErrorCode(int code, String messger,HttpStatusCode statusCode) {
        this.code = code;
        this.messger = messger;
        this.statusCode = statusCode;
    }

    private int code;
    private String messger;
    private HttpStatusCode statusCode;
}
