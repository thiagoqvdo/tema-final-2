package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component("playlistById")
public class PlaylistByIdCommand extends HystrixCommand<Mono<Playlist>> {

    @Autowired
    private PlaylistRepository playlistRepository;
    private UUID playlistId;

    protected PlaylistByIdCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("Playlists"));
    }

    public PlaylistByIdCommand(PlaylistRepository playlistRepository, UUID id) {
        super(HystrixCommandGroupKey.Factory.asKey("Playlists"));
        this.playlistRepository = playlistRepository;
        this.playlistId = id;
    }

    @Override
    protected Mono<Playlist> run() throws Exception {
        return playlistRepository.findById(playlistId);
    }

    @Override
    protected Mono<Playlist> getFallback() {
        System.err.println(super.getFallback());
        return super.getFallback();
    }

    public Mono<Playlist> executeCommand(UUID id) {
        return new PlaylistByIdCommand(this.playlistRepository, id).execute();
    }
}
