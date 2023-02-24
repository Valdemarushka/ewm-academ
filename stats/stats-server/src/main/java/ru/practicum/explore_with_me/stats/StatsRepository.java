package ru.practicum.explore_with_me.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.dto.ViewStatsInt;
import ru.practicum.explore_with_me.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stat, Integer> {

    @Query("SELECT app AS app, uri AS uri, COUNT(ip) AS hits " +
            "FROM Stat " +
            "WHERE :uris IS NULL OR uri IN :uris " +
            "AND timestamp BETWEEN :start AND :end " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsInt> getViews(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT uri AS uri, app AS app, COUNT(DISTINCT ip) AS hits " +
            "FROM Stat " +
            "WHERE :uris IS NULL OR uri IN :uris " +
            "AND timestamp BETWEEN :start AND :end " +
            "GROUP BY app, uri, ip " +
            "ORDER BY hits DESC ")
    List<ViewStatsInt> getUniqueViews(LocalDateTime start, LocalDateTime end, List<String> uris);
}
