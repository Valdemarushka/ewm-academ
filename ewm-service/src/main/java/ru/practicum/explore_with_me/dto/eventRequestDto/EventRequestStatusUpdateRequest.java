package ru.practicum.explore_with_me.dto.eventRequestDto;

import lombok.Data;
import ru.practicum.explore_with_me.model.EventRequestStateAction;

@Data
public class EventRequestStatusUpdateRequest {
    private Integer[] requestIds;
    private EventRequestStateAction status;
}
