package ru.practicum.explore_with_me.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class ApiError {
    private final String reason;
    private final String message;
    private final LocalDateTime timestamp;
    private final String status;
}
