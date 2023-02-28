package ru.practicum.explore_with_me.controllers.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.model.Compilation;
import ru.practicum.explore_with_me.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompilationPublicController {

    public final CompilationService compilationService;

    @GetMapping("/compilations/{compId}")
    public Compilation get(@PathVariable Integer compId) {
        return compilationService.getById(compId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/compilations")
    public List<Compilation> getAll(@RequestParam(required = false) Boolean pinned,
                                    @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                    @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return compilationService.getAll(pinned, from, size);
    }
}
