package ru.practicum.explore_with_me.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.compilationDto.NewCompilationDto;
import ru.practicum.explore_with_me.model.Compilation;
import ru.practicum.explore_with_me.service.CompilationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompilationAdminController {

    public final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public Compilation add(@RequestBody NewCompilationDto newCompilationDto) {
        return compilationService.create(newCompilationDto);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public Compilation put(@PathVariable Integer compId, @RequestBody NewCompilationDto compilationDto) {
        return compilationService.update(compId, compilationDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer compId) {
        compilationService.delete(compId);
    }
}
