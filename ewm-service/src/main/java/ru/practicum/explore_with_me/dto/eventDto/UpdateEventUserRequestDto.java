package ru.practicum.explore_with_me.dto.eventDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.explore_with_me.model.Location;

import java.time.LocalDateTime;

@Data
public class UpdateEventUserRequestDto {

    private int id;
    private String description;
    private String annotation;
    private Integer category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Integer initiator;
    private Boolean paid;
    private String title;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Location location;
    private String stateAction;
}
