package ru.practicum.explore_with_me.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.model.Stat;
import ru.practicum.explore_with_me.dto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository repository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public Stat create(Stat stat) {
        return repository.save(stat);
    }

    @Transactional(readOnly = true)
    public List<ViewStats> getStatistic(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);
        if (unique) {
            return repository.getUniqueViews(startDate, endDate, uris).stream()
                    .map(StatsMapper::toViewStats)
                    .collect(Collectors.toList());
        } else {
            return repository.getViews(startDate, endDate, uris).stream()
                    .map(StatsMapper::toViewStats)
                    .collect(Collectors.toList());
        }
    }
}
