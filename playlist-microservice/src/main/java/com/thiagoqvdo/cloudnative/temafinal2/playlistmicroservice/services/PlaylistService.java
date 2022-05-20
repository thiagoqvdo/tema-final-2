package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.services;

import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.hystrix.PlaylistHystrixCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service("service")
public class PlaylistService {
    
    @Autowired
    private PlaylistHystrixCommands commands;
    
    public Flux<Playlist> getAll() {
        return commands.getAll();
    }
    
    public Mono<Playlist> findById(UUID id) {
        return commands.getById(id);
    }
}
