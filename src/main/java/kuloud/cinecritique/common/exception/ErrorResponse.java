package kuloud.cinecritique.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime time;
    private String errorMessage;

    public static ResponseEntity<ErrorResponse> createWith(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .time(LocalDateTime.now())
                        .errorMessage(errorCode.getErrorMessage())
                        .build());
    }
}
