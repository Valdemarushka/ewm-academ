package ru.practicum.explore_with_me.dto.compilationDto;

import lombok.Data;

import java.util.Set;

@Data
public class NewCompilationDto {
    private int id;
    private Set<Integer> events;
    private boolean pinned;
    private String title;
}
