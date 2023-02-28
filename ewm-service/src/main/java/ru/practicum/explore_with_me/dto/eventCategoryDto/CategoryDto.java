package ru.practicum.explore_with_me.dto.eventCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CategoryDto {
    private int id;
    @NonNull
    private String name;
}
