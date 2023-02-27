package ru.practicum.explore_with_me.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.compilationDto.NewCompilationDto;
import ru.practicum.explore_with_me.exception.IncorrectCompilationBodyException;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.model.Compilation;
import ru.practicum.explore_with_me.model.Event;
import ru.practicum.explore_with_me.repository.CompilationRepository;
import ru.practicum.explore_with_me.repository.EventRepository;

import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CompilationService {

    @Getter
    private final CompilationRepository repository;

    private final EventRepository eventRepository;

    private final EventService eventService;

    @Transactional
    public Compilation create(NewCompilationDto compilationDto) {
        if (compilationDto.getTitle() == null) {
            throw new IncorrectCompilationBodyException("Поле title не должно быть пустым.",
                    "Неправильно сделанный запрос.");
        }

        Compilation compilation = new Compilation();

        compilation.setPinned(compilationDto.isPinned());
        compilation.setTitle(compilationDto.getTitle());
        compilation.setEventsIds(compilationDto.getEvents());

        if (!compilationDto.getEvents().isEmpty()) {
            List<Event> eventsInCompilation = fillEvents(compilationDto.getEvents());
            compilation.setEvents(eventsInCompilation);
        } else {
            compilation.setEvents(new ArrayList<>());
        }

        return repository.save(compilation);
    }

    @Transactional
    public Compilation getById(int compId) {
        Optional<Compilation> foundCompilation = repository.findById(compId);
        if (foundCompilation.isEmpty()) {
            throw new NotFoundException(String.format("Подборка с id=%d не найдена ", compId),
                    "Требуемый объект не был найден.");
        }

        Compilation compilation = foundCompilation.get();

        if (compilation.getEventsIds() != null && !compilation.getEventsIds().isEmpty()) {
            List<Event> eventsInCompilation = fillEvents(compilation.getEventsIds());
            compilation.setEvents(eventsInCompilation);
        } else {
            compilation.setEventsIds(new HashSet<>());
            compilation.setEvents(new ArrayList<>());
        }

        return compilation;
    }

    @Transactional
    public List<Compilation> getAll(Boolean pinned, Integer from, Integer size) {

        Page<Compilation> compilations = repository.findAll(
                where(isPinned(pinned)),
                PageRequest.of(from / size, size)
        );

        List<Compilation> foundCompilations = compilations.getContent();

        for (Compilation compilation : foundCompilations) {
            if (compilation.getEventsIds() != null && !compilation.getEventsIds().isEmpty()) {
                List<Event> eventsInCompilation = fillEvents(compilation.getEventsIds());
                compilation.setEvents(eventsInCompilation);
            } else {
                compilation.setEventsIds(new HashSet<>());
                compilation.setEvents(new ArrayList<>());
            }
        }

        return foundCompilations;
    }

    private Specification<Compilation> isPinned(Boolean pinned) {
        return (compilation, query, cb) -> pinned == null ? null : cb.equal(compilation.get("isPinned"), pinned);
    }

    @Transactional
    public Compilation update(int compId, NewCompilationDto compilationDto) {
        Optional<Compilation> foundCompilation = repository.findById(compId);
        if (foundCompilation.isEmpty()) {
            throw new NotFoundException(String.format("Подборка с id=%d не найдена", compId),
                    "Требуемый объект не был найден.");
        }
        Compilation compilation = foundCompilation.get();

        compilation.setTitle(compilationDto.getTitle() == null ? compilation.getTitle() : compilationDto.getTitle());
        compilation.setPinned(compilationDto.isPinned());

        if (compilation.getTitle() == null) {
            throw new IncorrectCompilationBodyException("Поле title не должно быть пустым.",
                    "Неправильно сделанный запрос.");
        }

        compilation.setEventsIds(new HashSet<>());

        if (!compilationDto.getEvents().isEmpty()) {
            List<Event> eventsInCompilation = fillEvents(compilationDto.getEvents());
            compilation.setEvents(eventsInCompilation);
            compilation.setEventsIds(compilationDto.getEvents());
        } else {
            compilation.setEvents(new ArrayList<>());
        }

        return repository.save(compilation);
    }

    private List<Event> fillEvents(Set<Integer> eventIds) {
        return eventService.fillInformation(eventRepository.findByIdIn(eventIds), null);
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Подборка с id=%d не найдена ", id),
                    "Требуемый объект не был найден.");
        }
    }
}
