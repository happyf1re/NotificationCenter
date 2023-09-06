package com.muravlev.core.services;

import com.muravlev.core.dto.PostDTO;
import com.muravlev.core.entities.Channel;
import com.muravlev.core.entities.Post;
import com.muravlev.core.entities.User;
import com.muravlev.core.mappers.PostMapper;
import com.muravlev.core.repositories.ChannelRepository;
import com.muravlev.core.repositories.PostRepository;
import com.muravlev.core.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final PostMapper postMapper;
    private final TelegramBotService telegramBotService;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ChannelRepository channelRepository, PostMapper postMapper, TelegramBotService telegramBotService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.postMapper = postMapper;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public PostDTO createPost(PostDTO postDto) {
        Post post = postMapper.convertToEntity(postDto);
        post.setCreationTime(LocalDateTime.now());
        User author = userRepository.findById(postDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID"));
        post.setAuthor(author);
        Channel channel = channelRepository.findById(postDto.getChannelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid channel ID"));
        post.setChannel(channel);
        post = postRepository.save(post);

        // Уведомление подписчиков
        List<User> subscribers = channel.getSubscribers();
        for (User subscriber : subscribers) {
            if (subscriber.getTelegramChatId() != null) {
                String message = "Новый пост в канале " + channel.getName() + ": " + post.getContent();
                telegramBotService.sendMessage(subscriber.getTelegramChatId(), message);
            }
        }

        return postMapper.convertToDto(post);
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        return postMapper.convertToDto(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDTO> getAllPostsByChannelId(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid channel ID"));
        List<Post> posts = postRepository.findAllByChannel(channel);
        return posts.stream()
                .map(postMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }
}
