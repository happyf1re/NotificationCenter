package com.muravlev.core.controllers;

import com.muravlev.core.dto.PostDTO;
import com.muravlev.core.services.PostService;
import com.muravlev.core.services.PostServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @GetMapping("/channel/{channelId}")
    public List<PostDTO> getAllPostsByChannelId(@PathVariable Long channelId) {
        return postService.getAllPostsByChannelId(channelId);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Long id) {
        return postService.existsById(id);
    }
}
