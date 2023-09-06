package com.muravlev.core.services;

import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.entities.*;
import com.muravlev.core.mappers.ChannelMapper;
import com.muravlev.core.mappers.UserMapper;
import com.muravlev.core.repositories.ChannelRepository;
import com.muravlev.core.repositories.InvitationRepository;
import com.muravlev.core.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelServiceImpl implements ChannelService{

    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private InvitationRepository invitationRepository;
    private ChannelMapper channelMapper;
    private UserMapper userMapper;
    private UserService userService;

    public ChannelServiceImpl(ChannelRepository channelRepository, UserRepository userRepository, InvitationRepository invitationRepository, ChannelMapper channelMapper, UserMapper userMapper, UserService userService) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.channelMapper = channelMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    @Transactional
    public ChannelDTO createChannel(ChannelDTO channelDto) {
        Channel channel = channelMapper.convertToEntity(channelDto);

        // Получение пользователя из репозитория
        User creator = userRepository.findById(channelDto.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("No such user"));

        channel.setCreator(creator);

        Channel createdChannel = channelRepository.save(channel);
        return channelMapper.convertToDto(createdChannel);
    }

    @Override
    public ChannelDTO getChannelById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));
        return channelMapper.convertToDto(channel);
    }

    @Override
    @Transactional
    public ChannelDTO updateChannel(Long id, ChannelDTO channelDto) {
        Channel existingChannel = channelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));
        existingChannel.setName(channelDto.getName());
        existingChannel.setType(channelDto.getType());
        channelRepository.save(existingChannel);
        return channelMapper.convertToDto(existingChannel);
    }

    @Override
    @Transactional
    public void deleteChannel(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));
        channelRepository.delete(channel);
    }

    @Override
    @Transactional
    public void setChannelType(Long id, ChannelType type) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));
        channel.setType(type);
        channelRepository.save(channel);
    }

    @Override
    public void subscribe(Long userId, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No such user"));
        user.getSubscribedChannels().add(channel);
        channel.getSubscribers().add(user);
        channelRepository.save(channel);
        userRepository.save(user);

    }

    @Override
    public void unsubscribe(Long userId, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No such user"));
        channel.getSubscribers().remove(user);
        user.getSubscribedChannels().remove(channel);
        userRepository.save(user);
        channelRepository.save(channel);
    }

    @Override
    @Transactional
    public void invite(Long inviterId, Long inviteeId, Long channelId) {
        User inviter = userMapper.convertToEntity(userService.getUserById(inviterId));
        User invitee = userMapper.convertToEntity(userService.getUserById(inviteeId));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("No such channel"));

        System.out.println(Arrays.toString(channel.getSubscribers().toArray()));
        System.out.println(channel.getId());

        if (channel.getType() == ChannelType.PRIVATE && !channel.getCreator().equals(inviter)) {
            throw new IllegalArgumentException("Only the creator can invite users to a private channel");
        }

        if (!channel.getSubscribers().contains(inviter) && !channel.getCreator().equals(inviter) ) {
            System.out.println(Arrays.toString(channel.getSubscribers().toArray()));
            throw new IllegalArgumentException("Inviter must be a subscriber of the channel");
        }

        if (channel.getSubscribers().contains(invitee)) {
            throw new IllegalArgumentException("Invitee is already a subscriber of the channel");
        }

        Invitation invitation = new Invitation();
        invitation.setChannel(channel);
        invitation.setInviter(inviter);
        invitation.setInvitee(invitee);
        invitation.setStatus(InvitationStatus.INVITED);
        invitationRepository.save(invitation);
    }


    @Transactional
    public void acceptInvitation(Long inviteeId, Long invitationId) {
        User invitee = userMapper.convertToEntity(userService.getUserById(inviteeId));
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("No such invitation"));

        if (!invitation.getInvitee().equals(invitee)) {
            throw new IllegalArgumentException("Only the invitee can accept the invitation");
        }

        if (invitation.getStatus() != InvitationStatus.INVITED) {
            throw new IllegalArgumentException("Invitation has already been responded to");
        }

        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.getInvitee().getSubscribedChannels().add(invitation.getChannel());
        invitation.getChannel().getSubscribers().add(invitee);
        userRepository.save(invitee);
        channelRepository.save(invitation.getChannel());
        invitationRepository.save(invitation);
    }


    @Transactional
    public void declineInvitation(Long inviteeId, Long invitationId) {
        User invitee = userMapper.convertToEntity(userService.getUserById(inviteeId));
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("No such invitation"));

        if (!invitation.getInvitee().equals(invitee)) {
            throw new IllegalArgumentException("Only the invitee can decline the invitation");
        }

        if (invitation.getStatus() != InvitationStatus.INVITED) {
            throw new IllegalArgumentException("Invitation has already been responded to");
        }

        invitation.setStatus(InvitationStatus.DECLINED);
        invitationRepository.save(invitation);
    }

    public boolean existsById(Long id) {
        return channelRepository.existsById(id);
    }

    public List<ChannelDTO> getAllPublicChannels() {
        List<Channel> publicChannels = channelRepository.findAllByType(ChannelType.PUBLIC);
        List<ChannelDTO> result = publicChannels.stream()
                .map(channelMapper::convertToDto)
                .collect(Collectors.toList());

        System.out.println(publicChannels);

        return result;
    }

    public List<ChannelDTO> getAllPrivateChannels(Long userId) {
        List<Channel> userChannels = channelRepository.findAllByCreatorIdOrSubscribersId(userId, userId);
        List<ChannelDTO> result = userChannels.stream()
                .filter(channel -> channel.getType() == ChannelType.PRIVATE)
                .map(channelMapper::convertToDto)
                .collect(Collectors.toList());

        System.out.println(result);

        return result;
    }
}
