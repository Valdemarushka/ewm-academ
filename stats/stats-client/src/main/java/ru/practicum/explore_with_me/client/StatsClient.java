package ru.practicum.explore_with_me.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explore_with_me.dto.StatDto;
import ru.practicum.explore_with_me.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsClient {

    @Value("${stats-server.url}")
    private String serverUrl;
    private final RestTemplate rest = new RestTemplate();

    public List<ViewStats> getStats(HttpServletRequest request) throws JsonProcessingException {
        String queryString = request.getQueryString();
        String queryUrl = serverUrl + "/stats" + (queryString.isBlank() ? "" : "?" + queryString);
        ResponseEntity<String> response = rest.getForEntity(URI.create(queryUrl), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ViewStats[] array = objectMapper.readValue(response.getBody(), ViewStats[].class);
        return Arrays.asList(array);
    }

    public void hit(StatDto statDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StatDto> requestEntity = new HttpEntity<>(statDto, headers);
        rest.exchange(serverUrl + "/hit", HttpMethod.POST, requestEntity, StatDto.class);
    }
}
