package ru.practicum.explore_with_me.dto.eventDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import ru.practicum.explore_with_me.model.Location;

import java.time.LocalDateTime;

@Data
public class NewEventDto {
    private int id;
    private String description;
    @NonNull
    private String annotation;
    private int category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private int initiator;
    private boolean paid;
    @NonNull
    private String title;
    private int participantLimit;
    private boolean requestModeration;
    private Location location;
}
