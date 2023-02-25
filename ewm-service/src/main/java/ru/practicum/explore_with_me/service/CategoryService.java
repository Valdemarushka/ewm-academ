package ru.practicum.explore_with_me.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.eventCategoryDto.CategoryDto;
import ru.practicum.explore_with_me.exception.CategoryNotEmptyException;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.mapper.CategoryMapper;
import ru.practicum.explore_with_me.model.Category;
import ru.practicum.explore_with_me.model.Event;
import ru.practicum.explore_with_me.repository.CategoryRepository;
import ru.practicum.explore_with_me.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    @Getter
    private final CategoryRepository repository;

    private final EventRepository eventRepository;

    @Transactional
    public Category create(Category category) {
        return repository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(int from, int size) {
        return repository.findAllByOrderById(PageRequest.of(from / size, size)).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Category getById(Integer id) {
        Optional<Category> category = repository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new NotFoundException(String.format("Категория с id=%d не найдена", id),
                    "Требуемый объект не найден.");
        }
    }

    @Transactional
    public Category put(int id, Category category) {
        category.setId(id);
        Optional<Category> previous = repository.findById(id);
        if (previous.isPresent()) {
            category.setName(
                    (category.getName() == null || category.getName().isBlank())
                            ? previous.get().getName()
                            : category.getName()
            );
        } else {
            throw new NotFoundException(String.format("Категория с id=%d не найдена", id),
                    "Требуемый объект не найден.");
        }
        return repository.save(category);
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.findById(id).isPresent()) {
            if (isCategoryEmpty(id)) {
                repository.deleteById(id);
            } else {
                throw new CategoryNotEmptyException("Категория не является пустой",
                        "Не соблюдены условия для запрошенной операции.");
            }
        } else {
            throw new NotFoundException(String.format("Категория с id=%d не найдена", id),
                    "Требуемый объект не найден.");
        }
    }

    private boolean isCategoryEmpty(int id) {
        List<Event> categoryEvents = eventRepository.findByCategoryId(id);
        return categoryEvents.isEmpty();
    }
}
