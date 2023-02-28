package ru.practicum.explore_with_me.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.mapper.CategoryMapper;
import ru.practicum.explore_with_me.service.CategoryService;
import ru.practicum.explore_with_me.dto.eventCategoryDto.CategoryDto;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CategoryAdminController {

    public final CategoryService categoryService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto add(@Valid @RequestBody CategoryDto categoryDto) {
        return CategoryMapper.toCategoryDto(categoryService.create(CategoryMapper.toCategory(categoryDto)));
    }

    @PatchMapping("/admin/categories/{catId}")
    public CategoryDto patch(@PathVariable Integer catId, @Valid @RequestBody CategoryDto categoryDto) {
        return CategoryMapper.toCategoryDto(categoryService.put(catId, CategoryMapper.toCategory(categoryDto)));
    }

    @DeleteMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer catId) {
        categoryService.delete(catId);
    }
}
