package com.muravlev.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDTO {
    private Long id;
    private Long inviterId;
    private Long inviteeId;
    private Long channelId;
    private String channelName;
    private String status;
}
