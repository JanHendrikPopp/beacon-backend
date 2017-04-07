package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.exceptions.GameNotFoundException;
import com.baecon.rockpapersissersapp.exceptions.UserNotFoundException;
import com.baecon.rockpapersissersapp.rest.response.ErrorResponse;
import com.baecon.rockpapersissersapp.rest.response.GameNotFoundErrorResponse;
import com.baecon.rockpapersissersapp.rest.response.MissingParameterErrorResponse;
import com.baecon.rockpapersissersapp.rest.response.UserNotFoundErrorResponse;
import com.baecon.rockpapersissersapp.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler{

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(MissingServletRequestParameterException exception) {
        return new MissingParameterErrorResponse(ErrorCodes.MISSING_PARAMETER, "Required parameter missing.", exception.getParameterName());
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        return new UserNotFoundErrorResponse(ErrorCodes.USER_NOT_FOUND, "Could not find user.", exception.getUserId());
    }

    @ExceptionHandler({GameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleGameNotFoundException(GameNotFoundException exception) {
        return new GameNotFoundErrorResponse(ErrorCodes.GAME_NOT_FOUND, "Could not find game.", exception.getGameId());
    }
}
