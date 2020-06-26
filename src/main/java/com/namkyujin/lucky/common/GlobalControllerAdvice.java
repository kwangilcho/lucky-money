package com.namkyujin.lucky.common;

import com.namkyujin.lucky.common.exception.UnauthorizedGameRoomUserException;
import com.namkyujin.lucky.common.model.CommonResponse;
import com.namkyujin.lucky.game.model.exeception.GameClosedException;
import com.namkyujin.lucky.game.model.exeception.GameNotExistException;
import com.namkyujin.lucky.game.model.exeception.ParticipateRestrictException;
import com.namkyujin.lucky.game.model.exeception.WinningPrizeNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UnauthorizedGameRoomUserException.class)
    public ResponseEntity<CommonResponse<Object>> handleUnauthorized(UnauthorizedGameRoomUserException e) {
        log(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.of(e.getMessage()));
    }

    @ExceptionHandler({
            GameClosedException.class,
            GameNotExistException.class,
            ParticipateRestrictException.class,
            WinningPrizeNotExistException.class,
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity handleBadRequest(Exception e) {
        log(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.of(e.getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class, Throwable.class})
    public ResponseEntity handleInternalServerError(Exception e) {
        log(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.of("일시적으로 문제가 발생했습니다. 잠시 후 시도해주세요."));
    }


    private void log(Throwable e) {
        log.error("Occurred Exception. message={}", e.getMessage(), e);
    }
}
