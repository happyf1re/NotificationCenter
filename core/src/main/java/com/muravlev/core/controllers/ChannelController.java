package com.muravlev.core.controllers;

import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.dto.InvitationDTO;
import com.muravlev.core.dto.UserDTO;
import com.muravlev.core.entities.ChannelType;
import com.muravlev.core.entities.User;
import com.muravlev.core.mappers.UserMapper;
import com.muravlev.core.services.ChannelServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
@CrossOrigin(origins = "http://localhost:3000")
public class ChannelController {

    private ChannelServiceImpl channelService;
    private UserMapper userMapper;

    public ChannelController(ChannelServiceImpl channelService, UserMapper userMapper) {
        this.channelService = channelService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public ChannelDTO createChannel(@RequestBody ChannelDTO channelDTO){
        return channelService.createChannel(channelDTO);
    }

    @GetMapping("/{id}")
    public ChannelDTO getChannelById(@PathVariable Long id){
        return channelService.getChannelById(id);
    }

    @PutMapping("/{id}")
    public ChannelDTO updateChannel(@PathVariable Long id, @RequestBody ChannelDTO channelDTO){
        return channelService.updateChannel(id, channelDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable Long id){
        channelService.deleteChannel(id);
    }

    @PutMapping("/{id}/type")
    public ResponseEntity<Void> setChannelType(@PathVariable Long id, @RequestBody ChannelType type) {
        channelService.setChannelType(id, type);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{channelId}/subscribe/{userId}")
    public ResponseEntity<Void> subscribe(@PathVariable Long userId, @PathVariable Long channelId){
        channelService.subscribe(userId, channelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{channelId}/unsubscribe/{userId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long userId, @PathVariable Long channelId){
        channelService.unsubscribe(userId, channelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@RequestBody InvitationDTO invitationDto) {
        channelService.invite(invitationDto.getInviterId(), invitationDto.getInviteeId(), invitationDto.getChannelId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invitations/{id}/accept")
    public ResponseEntity<Void> acceptInvitation(@RequestBody UserDTO userDto, @PathVariable Long id) {
        User user = userMapper.convertToEntity(userDto);
        channelService.acceptInvitation(user.getId(), id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invitations/{id}/decline")
    public ResponseEntity<Void> declineInvitation(@RequestBody UserDTO userDto, @PathVariable Long id) {
        User user = userMapper.convertToEntity(userDto);
        channelService.declineInvitation(user.getId(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Long id) {
        return channelService.existsById(id);
    }
}
