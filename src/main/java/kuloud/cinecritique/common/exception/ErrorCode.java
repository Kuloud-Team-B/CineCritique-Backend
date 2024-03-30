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
    NOT_EXIST_ADMIN(BAD_REQUEST, "관리자 계정이 일치하지 않습니다."),

    // favorite
    NOT_EXIST_FAVORITE(BAD_REQUEST, "해당 즐겨찾기를 확인할 수 없습니다."),

    // likes
    NOT_EXIST_LIKES(BAD_REQUEST, "해당 게시글의 좋아요를 확인할 수 없습니다."),

    // post
    NOT_EXIST_POST(BAD_REQUEST, "해당 게시글이 존재하지 않습니다."),

    // goods
    NOT_EXIST_GOODS(BAD_REQUEST, "해당 굿즈가 존재하지 않습니다."),
    DUPLICATED_GOODS_NAME(BAD_REQUEST, "중복된 이름의 굿즈가 존재합니다."),

    // cinema
    NOT_EXIST_CINEMA(BAD_REQUEST, "해당 영화관이 존재하지 않습니다."),
    DUPLICATED_CINEMA_NAME(BAD_REQUEST, "중복된 이름의 영화관이 존재합니다."),

    // company
    NOT_EXIST_COMPANY(BAD_REQUEST, "해당 회사가 존재하지 않습니다."),
    DUPLICATED_COMPANY_NAME(BAD_REQUEST, "중복된 이름의 회사가 존재합니다."),

    // movie
    NOT_EXIST_MOVIE(BAD_REQUEST, "해당 영화를 찾을 수 없습니다."),
    DUPLICATED_MOVIE_NAME(BAD_REQUEST, "중복된 이름의 영화가 존재합니다."),

    // member
    DUPLICATED_NICKNAME(BAD_REQUEST, "중복된 닉네임입니다."),
    DUPLICATED_EMAIL(BAD_REQUEST,"중복된 이메일입니다."),
    NOT_EXIST_MEMBER(BAD_REQUEST, "해당 사용자를 찾을 수 없습니다."),
    NOT_EXIST_EMAIL(BAD_REQUEST, "해당 이메일을 찾을 수 없습니다."),
    BAD_PARAMETER(BAD_REQUEST, "양식에 맞춰서 입력해주세요."),
    INVALID_LOGIN(BAD_REQUEST, "로그인 정보가 맞지 않습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;
}
