package kuloud.cinecritique.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        log.error("throw CustomException : {}", customException.getErrorCode());
        return ErrorResponse.createWith(customException.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException validException) {
        log.error("convert MethodArgumentNotValidException to CustomException : {}", validException.getMessage());
        return ErrorResponse.createWith(new CustomException(ErrorCode.BAD_PARAMETER).getErrorCode());
    }
}
