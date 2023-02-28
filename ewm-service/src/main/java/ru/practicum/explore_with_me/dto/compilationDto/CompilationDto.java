package ru.practicum.explore_with_me.dto.compilationDto;

import lombok.Data;
import ru.practicum.explore_with_me.dto.eventDto.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private int id;
    private boolean pinned;
    private String title;
    private List<EventShortDto> eventsObj;
}
