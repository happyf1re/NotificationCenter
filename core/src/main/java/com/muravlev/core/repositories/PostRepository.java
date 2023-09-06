package com.muravlev.core.repositories;

import com.muravlev.core.entities.Channel;
import com.muravlev.core.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByChannelId(Long channelId);
    List<Post> findAllByChannel(Channel channel);
    boolean existsById(Long id);
}
