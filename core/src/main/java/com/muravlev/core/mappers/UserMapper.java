package com.muravlev.core.mappers;

import com.muravlev.core.dto.UserDTO;
import com.muravlev.core.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO convertToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public User convertToEntity(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
