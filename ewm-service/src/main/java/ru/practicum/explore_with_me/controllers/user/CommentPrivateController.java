package ru.practicum.explore_with_me.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.comment.CommentDto;
import ru.practicum.explore_with_me.mapper.CommentMapper;
import ru.practicum.explore_with_me.service.EventService;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentPrivateController {

    public final EventService eventService;

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto add(@PathVariable Integer userId, @PathVariable Integer eventId,
                          @Valid @RequestBody CommentDto commentDto) {
        return CommentMapper.toCommentDto(eventService.addComment(userId, eventId, CommentMapper.toComment(commentDto)));
    }

    @PatchMapping("/users/{userId}/events/{eventId}/comments/{commentId}")
    public CommentDto put(@PathVariable Integer userId, @PathVariable Integer eventId, @PathVariable Integer commentId,
                          @Valid @RequestBody CommentDto commentDto) {
        return CommentMapper.toCommentDto(
                eventService.putComment(userId, eventId, commentId, CommentMapper.toComment(commentDto))
        );
    }

    @DeleteMapping("/users/{userId}/events/{eventId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId, @PathVariable Integer eventId, @PathVariable Integer commentId) {
        eventService.deleteComment(userId, eventId, commentId);
    }
}
