package ru.practicum.explore_with_me.controllers.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.eventCategoryDto.CategoryDto;
import ru.practicum.explore_with_me.mapper.CategoryMapper;
import ru.practicum.explore_with_me.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CategoryPublicController {

    public final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> getAll(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                    @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Get categories by params: " +
                        "from {}, " +
                        "size {} ",
                from, size);

        return categoryService.getAll(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto get(@PathVariable Integer catId) {
        return CategoryMapper.toCategoryDto(categoryService.getById(catId));
    }
}
