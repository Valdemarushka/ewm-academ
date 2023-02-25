package ru.practicum.explore_with_me.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.model.EventRequest;
import ru.practicum.explore_with_me.dto.eventRequestDto.EventRequestDto;

@Component
public class RequestMapper {

    public static EventRequestDto toEventRequestDto(EventRequest eventRequest) {
        EventRequestDto eventRequestDto = new EventRequestDto();
        eventRequestDto.setId(eventRequest.getId());
        eventRequestDto.setRequester(eventRequest.getRequesterId());
        eventRequestDto.setEvent(eventRequest.getEventId());
        eventRequestDto.setCreated(eventRequest.getCreated());
        eventRequestDto.setStatus(eventRequest.getStatus());
        return eventRequestDto;
    }
}
