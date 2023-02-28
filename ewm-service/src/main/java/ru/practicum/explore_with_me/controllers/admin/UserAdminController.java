package ru.practicum.explore_with_me.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.mapper.UserMapper;
import ru.practicum.explore_with_me.service.UserService;
import ru.practicum.explore_with_me.dto.user.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    public final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto add(@Valid @RequestBody UserDto userDto) {
        return UserMapper.toUserDto(userService.create(UserMapper.toUser(userDto)));
    }

    @GetMapping
    public List<UserDto> getAll(@RequestParam(required = false) Integer[] ids,
                                @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return userService.getAll(ids, from, size)
                .stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        userService.delete(userId);
    }

}
