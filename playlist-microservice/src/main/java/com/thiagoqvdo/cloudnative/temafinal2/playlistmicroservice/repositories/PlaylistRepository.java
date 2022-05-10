package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.repositories;

import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PlaylistRepository extends ReactiveCassandraRepository<Playlist, Integer> {
    Mono<Playlist> findById(UUID id);
}
