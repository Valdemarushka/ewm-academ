package ru.practicum.explore_with_me.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAllByOrderById(Pageable pageable);
}
