package ru.practicum.explore_with_me.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.model.User;
import ru.practicum.explore_with_me.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Getter
    private final UserRepository repository;

    @Transactional
    public User create(User user) {
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<User> getAll(Integer[] ids, int from, int size) {
        if (ids != null && ids.length > 0) {
            List<Integer> id = new ArrayList<>(Arrays.asList(ids));
            return repository.findAllByIdInOrderById(id, PageRequest.of(from / size, size));
        }
        return repository.findAllByOrderById(PageRequest.of(from / size, size));
    }

    @Transactional
    public void delete(Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Пользователь с id=%d не найден", id),
                    "Требуемый объект не был найден.");
        }
    }
}
