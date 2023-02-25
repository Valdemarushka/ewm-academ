package ru.practicum.explore_with_me.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
public class IncorrectCompilationBodyException extends ValidationException {

    private final String message;
    @Getter
    private final String reason;

    @Override
    public String getMessage() {
        return message;
    }
}
