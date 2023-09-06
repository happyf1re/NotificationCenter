package com.muravlev.core.services;

import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.entities.ChannelType;
import com.muravlev.core.entities.User;

public interface ChannelService {
    ChannelDTO createChannel(ChannelDTO channelDto);
    ChannelDTO getChannelById(Long id);
    ChannelDTO updateChannel(Long id, ChannelDTO channelDto);
    void deleteChannel(Long id);
    void setChannelType(Long id, ChannelType type);
    void subscribe(Long userId, Long channelId);
    void unsubscribe(Long userId, Long channelId);
    void invite(Long inviterId, Long inviteeId, Long channelId);
}

