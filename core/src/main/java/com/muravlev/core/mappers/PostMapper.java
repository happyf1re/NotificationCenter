package com.muravlev.core.mappers;

import com.muravlev.core.dto.PostDTO;
import com.muravlev.core.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO convertToDto(Post post) {
        PostDTO postDto = new PostDTO();

        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setImageUrl(post.getImageUrl());
        if (post.getAuthor() != null) {
            postDto.setAuthorId(post.getAuthor().getId());
            postDto.setAuthorName(post.getAuthor().getName());
        }
        if (post.getChannel() != null) {
            postDto.setChannelId(post.getChannel().getId());
        }
        postDto.setCreationTime(post.getCreationTime());

        return postDto;
    }

    public Post convertToEntity(PostDTO postDto) {
        Post post = new Post();

        post.setId(postDto.getId());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());

        return post;
    }
}
