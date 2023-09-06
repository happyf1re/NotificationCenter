package com.muravlev.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRequest {
    private Long inviterId;
    private Long inviteeId;
    private Long channelId;
}
