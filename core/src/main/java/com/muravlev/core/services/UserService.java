package com.muravlev.core.services;


import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.dto.InvitationDTO;
import com.muravlev.core.dto.UserDTO;
import com.muravlev.core.entities.Channel;
import com.muravlev.core.entities.Invitation;
import com.muravlev.core.entities.User;
import com.muravlev.core.mappers.ChannelMapper;
import com.muravlev.core.mappers.InvitationMapper;
import com.muravlev.core.mappers.UserMapper;
import com.muravlev.core.repositories.InvitationRepository;
import com.muravlev.core.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;
    private InvitationRepository invitationRepository;
    private UserMapper userMapper;
    private ChannelMapper channelMapper;
    private InvitationMapper invitationMapper;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, InvitationRepository invitationRepository, UserMapper userMapper, ChannelMapper channelMapper, InvitationMapper invitationMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.invitationRepository = invitationRepository;
        this.userMapper = userMapper;
        this.channelMapper = channelMapper;
        this.invitationMapper = invitationMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.convertToEntity(userDTO);
        System.out.println("ENCODING =======================================================================");
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        System.out.println("ENCODING =======================================================================");
        User createdUser = repository.save(user);
        System.out.println("ENCODING =======================================================================");
        System.out.println(user.getPassword());
        return userMapper.convertToDto(createdUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such user"));
        return userMapper.convertToDto(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such user"));
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setName(userDTO.getName());
        existingUser.setPassword(userDTO.getPassword());
        repository.save(existingUser);
        return userMapper.convertToDto(existingUser);
    }

    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such user"));
        repository.delete(user);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public boolean authenticate(String username, String password) {
        User user = repository.findByName(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public boolean existsByUsername(String username) {
        return repository.existsByName(username);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public List<ChannelDTO> getUserSubscriptions(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getSubscribedChannels()
                .stream()
                .map(channelMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<InvitationDTO> getUserInvitations(Long userId) {
        return invitationRepository.findByInviteeId(userId)
                .stream()
                .map(invitationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserByUsername(String username) {
        User user = repository.findByName(username);
        return user != null ? userMapper.convertToDto(user) : null;
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public Optional<User> findByTelegramChatId(Long telegramChatId) {
        return repository.findByTelegramChatId(telegramChatId);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
}
