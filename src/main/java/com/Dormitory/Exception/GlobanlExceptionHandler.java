package com.Dormitory.Exception;

import com.Dormitory.Dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobanlExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> hedlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessger(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessger());
        return ResponseEntity.badRequest().body(apiResponse);
    }



    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> hedlingAppeException(AppException appException) {
         ErrorCode errorCode = appException.getErrorCode();
         ApiResponse apiResponse = new ApiResponse();

         apiResponse.setCode(errorCode.getCode());
         apiResponse.setMessger(errorCode.getMessger());
          return ResponseEntity.status(errorCode.getStatusCode())
                  .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException accessDeniedException) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .messger(errorCode.getMessger())
                        .build());
    }


//MethodArgumentNotValidException.
//Đây là ngoại lệ thường gặp khi có lỗi xác thực dữ liệu đầu vào (như form validation lỗi).
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingVadatetion(MethodArgumentNotValidException exception) {
        String enumkey = exception.getFieldError().getDefaultMessage();

         ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

             try {
              errorCode = ErrorCode.valueOf(enumkey);
             }catch (IllegalArgumentException e) {

             }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessger(errorCode.getMessger());

          return  ResponseEntity.badRequest().body(apiResponse);
    }
}
