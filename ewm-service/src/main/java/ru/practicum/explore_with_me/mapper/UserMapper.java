package ru.practicum.explore_with_me.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.model.User;
import ru.practicum.explore_with_me.dto.user.UserDto;
import ru.practicum.explore_with_me.dto.user.UserShortDto;

@Component
public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public static User toUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public static UserShortDto toUserShortDto(User user) {
        return modelMapper.map(user, UserShortDto.class);
    }

    public static User toUser(UserShortDto userShortDto) {
        return modelMapper.map(userShortDto, User.class);
    }
}
