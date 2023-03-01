package ru.practicum.explore_with_me.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.dto.comment.CommentDto;
import ru.practicum.explore_with_me.dto.eventDto.*;
import ru.practicum.explore_with_me.model.Comment;
import ru.practicum.explore_with_me.model.Event;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    public static Event toEvent(NewEventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setIsPaid(eventDto.isPaid());
        event.setEventDate(eventDto.getEventDate());
        event.setTitle(eventDto.getTitle());
        event.setRequestModeration(eventDto.isRequestModeration());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setLocation(eventDto.getLocation());
        event.setInitiatorId(eventDto.getInitiator());
        event.setCategoryId(eventDto.getCategory());
        return event;
    }

    public static Event toEvent(UpdateEventUserRequestDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setIsPaid(eventDto.getPaid());
        event.setEventDate(eventDto.getEventDate());
        event.setTitle(eventDto.getTitle());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setLocation(eventDto.getLocation());
        event.setInitiatorId(eventDto.getInitiator());
        event.setCategoryId(eventDto.getCategory());
        return event;
    }

    public static Event toEvent(UpdateEventAdminRequestDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setIsPaid(eventDto.getPaid());
        event.setEventDate(eventDto.getEventDate());
        event.setTitle(eventDto.getTitle());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setLocation(eventDto.getLocation());
        event.setCategoryId(eventDto.getCategory());
        return event;
    }

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto newEventDto = new EventFullDto();
        newEventDto.setId(event.getId());
        newEventDto.setAnnotation(event.getAnnotation());
        newEventDto.setDescription(event.getDescription());
        newEventDto.setPaid(event.getIsPaid());
        newEventDto.setEventDate(event.getEventDate());
        newEventDto.setTitle(event.getTitle());
        newEventDto.setRequestModeration(event.getRequestModeration());
        newEventDto.setParticipantLimit(event.getParticipantLimit());
        newEventDto.setLocation(event.getLocation());
        newEventDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        newEventDto.setCategory(event.getCategory());
        newEventDto.setCreatedOn(event.getCreatedOn());
        newEventDto.setPublishedOn(event.getPublishedOn());
        newEventDto.setState(event.getState());
        List<CommentDto> commentsDto = new ArrayList<>();
        List<Comment> comments = event.getComments();
        if (comments != null) {
            for (Comment comment : comments) {
                commentsDto.add(CommentMapper.toCommentDto(comment));
            }
            newEventDto.setComments(commentsDto);
        }

        return newEventDto;
    }

    public static EventShortDto toEventShortDto(Event event) {
        EventShortDto newEventDto = new EventShortDto();
        newEventDto.setId(event.getId());
        newEventDto.setAnnotation(event.getAnnotation());
        newEventDto.setPaid(event.getIsPaid());
        newEventDto.setEventDate(event.getEventDate());
        newEventDto.setTitle(event.getTitle());
        newEventDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        newEventDto.setCategory(event.getCategory());
        List<CommentDto> commentsDto = new ArrayList<>();
        List<Comment> comments = event.getComments();
        if (comments != null) {
            for (Comment comment : comments) {
                commentsDto.add(CommentMapper.toCommentDto(comment));
            }
            newEventDto.setComments(commentsDto);
        }
        return newEventDto;
    }
}
