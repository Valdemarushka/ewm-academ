package ru.practicum.explore_with_me.stats;

import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.dto.StatDto;
import ru.practicum.explore_with_me.dto.ViewStats;
import ru.practicum.explore_with_me.dto.ViewStatsInt;
import ru.practicum.explore_with_me.model.Stat;

@Component
public class StatsMapper {
    public static Stat toStat(StatDto statDto) {
        return Stat.builder()
                .app(statDto.getApp())
                .uri(statDto.getUri())
                .ip(statDto.getIp())
                .timestamp(statDto.getTimestamp())
                .build();
    }

    public static ViewStats toViewStats(ViewStatsInt viewStats) {
        return new ViewStats(viewStats.getApp(), viewStats.getUri(), viewStats.getHits());
    }
}
