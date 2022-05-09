package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.repositories;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SongRepository extends ReactiveCassandraRepository<Song, Integer> {
    Mono<Song> findById(UUID id);
}
