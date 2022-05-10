package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("allPlaylists")
public class AllPlaylistsCommand extends HystrixCommand<Flux<Playlist>> {

    @Autowired
    private PlaylistRepository playlistRepository;

    protected AllPlaylistsCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("Playlists"));
    }

    private AllPlaylistsCommand(PlaylistRepository playlistRepository) {
        super(HystrixCommandGroupKey.Factory.asKey("Playlists"));
        this.playlistRepository = playlistRepository;
    }

    @Override
    protected Flux<Playlist> run() throws Exception {
        return playlistRepository.findAll();
    }

    @Override
    protected Flux<Playlist> getFallback() {
        return super.getFallback();
    }

    public Flux<Playlist> executeCommand() {
        return new AllPlaylistsCommand(this.playlistRepository).execute();
    }
}
