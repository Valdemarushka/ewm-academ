package ru.practicum.explore_with_me.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.StatDto;
import ru.practicum.explore_with_me.dto.ViewStats;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(required = false, defaultValue = "false") boolean unique) {
        log.info("Get statistic by params: " +
                        "start {}, " +
                        "end {}, " +
                        "uris {}, " +
                        "unique {}",
                start, end, uris, unique);
        return statsService.getStatistic(start, end, uris, unique);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void addStat(@RequestBody StatDto statDto) {
        log.info("Create statHit {}", statDto);
        statsService.create(statDto);
    }
}
