package ru.practicum.explore_with_me.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.explore_with_me.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer>, JpaSpecificationExecutor<Compilation> {
}
