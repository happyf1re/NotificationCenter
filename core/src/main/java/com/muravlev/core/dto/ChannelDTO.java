package com.muravlev.core.dto;


import com.muravlev.core.entities.Channel;
import com.muravlev.core.entities.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO {
    private Long id;
    private String name;
    private ChannelType type;
    private List<UserDTO> subscribers;
    //private UserDTO creator;
    private Long creatorId;
}
