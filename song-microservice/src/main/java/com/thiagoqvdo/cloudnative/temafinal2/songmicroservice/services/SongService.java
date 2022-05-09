package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.services;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.enums.HystrixCommandType;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix.SongHystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service("service")
public class SongService {

    @Autowired
    private SongHystrixCommand commands;

    public Flux<Song> getAllSongs() {
        return commands.execute(HystrixCommandType.GET_ALL);
    }

    public Mono<Song> getSongById(UUID id) {
        return (Mono<Song>) commands.execute(HystrixCommandType.GET_BY_ID, id);
    }
}
