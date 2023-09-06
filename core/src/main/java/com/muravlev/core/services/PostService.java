package com.muravlev.core.services;

import com.muravlev.core.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDto);
    PostDTO getPostById(Long id);
    void deletePost(Long id);
    List<PostDTO> getAllPostsByChannelId(Long channelId);
}
