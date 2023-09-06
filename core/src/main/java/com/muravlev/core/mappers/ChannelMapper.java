package com.muravlev.core.mappers;

import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.dto.UserDTO;
import com.muravlev.core.entities.Channel;
import com.muravlev.core.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChannelMapper {
    private final UserMapper userMapper;

    public ChannelMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ChannelDTO convertToDto(Channel channel) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        dto.setType(channel.getType());
        dto.setCreatorId(channel.getCreator().getId());

        // Add subscribers
        List<UserDTO> subscribers = channel.getSubscribers().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
        dto.setSubscribers(subscribers);

        return dto;
    }

    public Channel convertToEntity(ChannelDTO dto) {
        Channel channel = new Channel();
        channel.setId(dto.getId());
        channel.setName(dto.getName());
        channel.setType(dto.getType());

        User creator = new User();
        creator.setId(dto.getCreatorId()); // использование getCreatorId()
        channel.setCreator(creator);

        return channel;
    }
}
