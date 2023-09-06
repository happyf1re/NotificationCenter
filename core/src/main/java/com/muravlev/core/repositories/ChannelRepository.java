package com.muravlev.core.repositories;

import com.muravlev.core.entities.Channel;
import com.muravlev.core.entities.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    boolean existsById(Long id);
    List<Channel> findAllByType(ChannelType type);

    List<Channel> findAllByCreatorIdOrSubscribersId(Long creatorId, Long subscriberId);
}
