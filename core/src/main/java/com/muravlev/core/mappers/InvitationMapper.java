package com.muravlev.core.mappers;

import com.muravlev.core.dto.InvitationDTO;
import com.muravlev.core.entities.Invitation;
import com.muravlev.core.entities.InvitationStatus;
import org.springframework.stereotype.Component;

@Component
public class InvitationMapper {

    public InvitationDTO toDTO(Invitation invitation) {
        if (invitation == null) {
            return null;
        }

        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitation.getId());
        dto.setChannelId(invitation.getChannel().getId());
        dto.setInviterId(invitation.getInviter().getId());
        dto.setInviteeId(invitation.getInvitee().getId());
        dto.setChannelName(invitation.getChannel().getName());
        dto.setStatus(invitation.getStatus().name()); // преобразуем Enum в строку

        return dto;
    }

    public Invitation toEntity(InvitationDTO dto) {
        if (dto == null) {
            return null;
        }

        Invitation invitation = new Invitation();


        invitation.setId(dto.getId());
        invitation.setStatus(InvitationStatus.valueOf(dto.getStatus())); // преобразуем строку в Enum

        return invitation;
    }
}
