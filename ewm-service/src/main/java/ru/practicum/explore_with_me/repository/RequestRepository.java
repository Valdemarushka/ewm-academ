package ru.practicum.explore_with_me.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.model.EventRequest;
import ru.practicum.explore_with_me.model.EventRequestState;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<EventRequest, Integer> {

    Optional<EventRequest> findByRequesterIdAndEventId(int requesterId, int eventId);

    Page<EventRequest> findAllByRequesterId(int requesterId, Pageable pageable);

    Page<EventRequest> findAllByEventId(int eventId, Pageable pageable);

    List<EventRequest> findByEventIdAndIdInOrderById(int eventId, Collection<Integer> id);

    List<EventRequest> findByEventIdAndStatus(int eventId, EventRequestState status);

    List<EventRequest> findByEventIdInAndStatus(Collection<Integer> eventId, EventRequestState status);
}
