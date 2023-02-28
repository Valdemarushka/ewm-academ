package ru.practicum.explore_with_me.exception;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
    }

    @ExceptionHandler(CategoryNotEmptyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleCategoryNotEmptyException(final CategoryNotEmptyException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(EventDateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventDateException(final EventDateException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(EventStatusException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventStatusException(final EventStatusException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(EventStatusAdminException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventStatusAdminException(final EventStatusAdminException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(EventOwnerException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventOwnerException(final EventOwnerException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(DoubleEventRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDoubleEventRequestException(final DoubleEventRequestException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleValidationException(final ValidationException e) {
        return new ApiError(
                "Неправильно сделанный запрос.",
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolationException(final ConstraintViolationException e) {
        return new ApiError(
                "Нарушение целостности.",
                e.getSQLException().getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }

    @ExceptionHandler(IncorrectCompilationBodyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIncorrectCompilationBodyException(final IncorrectCompilationBodyException e) {
        return new ApiError(
                "Неправильно сделанный запрос.",
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.getReasonPhrase()
        );
    }
}
