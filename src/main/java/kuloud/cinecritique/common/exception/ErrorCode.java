package kuloud.cinecritique.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // authorization
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "접근 권한이 없습니다."),

    // horse
    NOT_EXIST_HORSE(BAD_REQUEST, "조건에 맞는 말이 없습니다."),

    // jockey

    // race

    // racetrack

    // member
    DUPLICATED_NICKNAME(BAD_REQUEST, "중복된 닉네임입니다."),
    NOT_EXIST_MEMBER(BAD_REQUEST, "해당 이름의 사용자를 찾을 수 없습니다."),
    NOT_EXIST_EMAIL(BAD_REQUEST, "해당 이메일을 찾을 수 없습니다."),
    BAD_PARAMETER(BAD_REQUEST, "양식에 맞춰서 입력해주세요."),
    INVALID_LOGIN(BAD_REQUEST, "비밀번호가 다릅니다.")
    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;
}
