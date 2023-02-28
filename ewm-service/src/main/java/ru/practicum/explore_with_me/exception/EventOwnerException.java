package ru.practicum.explore_with_me.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@ResponseStatus(HttpStatus.CONFLICT)
@RequiredArgsConstructor
public class EventOwnerException extends ValidationException {

    private final String message;
    @Getter
    private final String reason;

    @Override
    public String getMessage() {
        return message;
    }
}
