package ru.practicum.explore_with_me.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.eventRequestDto.EventRequestDto;
import ru.practicum.explore_with_me.dto.eventRequestDto.EventRequestStatusUpdateRequest;
import ru.practicum.explore_with_me.dto.eventRequestDto.EventRequestStatusUpdateResult;
import ru.practicum.explore_with_me.exception.EventOwnerException;
import ru.practicum.explore_with_me.exception.EventStatusException;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.mapper.RequestMapper;
import ru.practicum.explore_with_me.model.*;
import ru.practicum.explore_with_me.repository.EventRepository;
import ru.practicum.explore_with_me.repository.RequestRepository;
import ru.practicum.explore_with_me.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    @Getter
    private final RequestRepository repository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    @Transactional
    public EventRequest create(Integer userId, Integer eventId) {

        Optional<EventRequest> foundRequest = repository.findByRequesterIdAndEventId(userId, eventId);
        if (foundRequest.isPresent()) {
            throw new EventOwnerException(String.format("Запрос на событие %d от пользователя %d уже существует.",
                    eventId, userId), "Нарушено ограничение целостности");
        }

        Event event = findEvent(eventId);
        checkEventOwner(event, event.getInitiatorId(), userId);

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new EventStatusException(String.format("Событие %d еще не опубликовано.", eventId),
                    "Нарушено ограничение целостности.");
        }

        int confirmedRequests = repository.findByEventIdAndStatus(eventId, EventRequestState.CONFIRMED).size();
        int participantLimit = event.getParticipantLimit();
        if (confirmedRequests >= participantLimit) {
            throw new EventStatusException(String.format("Превышен лимит запроса события %d.", eventId),
                    "Нарушено ограничение целостности.");
        }

        EventRequest request = new EventRequest();

        request.setRequester(findUser(userId));
        request.setRequesterId(userId);
        request.setEventId(eventId);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        request.setStatus(event.getRequestModeration() ? EventRequestState.PENDING : EventRequestState.CONFIRMED);

        return repository.save(request);
    }

    @Transactional
    public EventRequest cancel(Integer userId, Integer requestId) {

        Optional<EventRequest> foundRequest = repository.findById(requestId);
        if (foundRequest.isEmpty()) {
            throw new NotFoundException(String.format("Запрос с идентификатором=%d не найден", requestId),
                    "Требуемый объект не был найден");
        }
        EventRequest request = foundRequest.get();

        findUser(userId);
        validateRequest(request, userId);
        request.setStatus(EventRequestState.CANCELED);

        return repository.save(request);
    }

    @Transactional(readOnly = true)
    public Page<EventRequest> getByUser(int userId, int from, int size) {
        findUser(userId);
        return repository.findAllByRequesterId(userId, PageRequest.of(from / size, size));
    }

    @Transactional(readOnly = true)
    public Page<EventRequest> getByUserAndEvent(int userId, int eventId, int from, int size) {
        findUser(userId);
        Event event = findEvent(eventId);
        checkEventOwner(event, userId, null);

        return repository.findAllByEventId(eventId, PageRequest.of(from / size, size));
    }

    @Transactional
    public EventRequestStatusUpdateResult updateRequests(int userId, int eventId,
                                                         EventRequestStatusUpdateRequest requestsResult) {
        Event event = findEvent(eventId);

        if ((event.getParticipantLimit() == null || event.getParticipantLimit() == 0)
                || (event.getRequestModeration() != null && !event.getRequestModeration())) {
            return null;
        }

        findUser(userId);
        checkEventOwner(event, userId, null);

        List<EventRequest> requests = changeRequestsStatus(event, requestsResult);
        repository.saveAll(requests);

        List<EventRequestDto> confirmed = requests.stream()
                .filter(x -> x.getStatus().equals(EventRequestState.CONFIRMED))
                .map(RequestMapper::toEventRequestDto).collect(Collectors.toList());
        List<EventRequestDto> rejected = requests.stream()
                .filter(x -> x.getStatus().equals(EventRequestState.REJECTED))
                .map(RequestMapper::toEventRequestDto).collect(Collectors.toList());

        return new EventRequestStatusUpdateResult(confirmed, rejected);
    }

    private Event findEvent(Integer eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            throw new NotFoundException(String.format("Событие с идентификатором=%d не найдено", eventId),
                    "Требуемый объект не был найден.");
        }
        return foundEvent.get();
    }

    private User findUser(Integer userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%d не найден", userId),
                    "Требуемый объект не был найден.");
        }
        return foundUser.get();
    }

    private void checkEventOwner(Event event, Integer initiatorId, Integer requestserId) {
        if (!event.getInitiatorId().equals(initiatorId)) {
            throw new EventOwnerException(String.format("Пользователь %d не является владельцем события %s.", initiatorId, event.getId()),
                    "Нарушено ограничение целостности");
        }
        if (event.getInitiatorId().equals(requestserId)) {
            throw new EventOwnerException(String.format("Пользователь %d является владельцем события %s", requestserId, event.getId()),
                    "Нарушено ограничение целостности.");
        }
    }

    private void validateRequest(EventRequest request, Integer userId) {
        if (request.getRequesterId() != userId) {
            throw new EventOwnerException(String.format("Пользователь с id=%d не является запрашивающим", userId),
                    "Требуемый объект не был найден.");
        }
    }

    private List<EventRequest> changeRequestsStatus(Event event, EventRequestStatusUpdateRequest requestsResult) {
        int confirmedRequests = repository.findByEventIdAndStatus(event.getId(), EventRequestState.CONFIRMED).size();
        int participantLimit = event.getParticipantLimit() == null ? 0 : event.getParticipantLimit();
        if (confirmedRequests >= participantLimit) {
            throw new EventStatusException("Лимит участников достигнут",
                    "Для запрошенной операции условия не соблюдены.");
        }

        List<EventRequest> requests = repository.findByEventIdAndIdInOrderById(event.getId(), List.of(requestsResult.getRequestIds()));
        EventRequestStateAction stateAction = requestsResult.getStatus();
        for (EventRequest request : requests) {
            if (!request.getStatus().equals(EventRequestState.PENDING)) {
                throw new EventStatusException(String.format("Запрос %s не находится в состоянии ожидания.", request.getId()),
                        "Для запрошенной операции условия не соблюдены.");
            }
            if (stateAction.equals(EventRequestStateAction.CONFIRMED)) {
                if (confirmedRequests < participantLimit) {
                    request.setStatus(EventRequestState.CONFIRMED);
                    confirmedRequests++;
                } else {
                    request.setStatus(EventRequestState.REJECTED);
                }
            } else {
                request.setStatus(EventRequestState.REJECTED);
            }
        }
        return requests;
    }
}
