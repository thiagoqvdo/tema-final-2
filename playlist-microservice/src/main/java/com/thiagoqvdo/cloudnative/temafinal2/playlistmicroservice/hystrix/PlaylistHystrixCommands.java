package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component("hystrixCommands")
public class PlaylistHystrixCommands {

    @Autowired
    private PlaylistRepository playlistRepository;

    public Flux<Playlist> getAll() {
        return new AllPlaylistsCommand().execute();
    }

    public Mono<Playlist> getById(UUID id) {
        return new PlaylistByIdCommand(id).execute();
    }

    private class AllPlaylistsCommand extends HystrixCommand<Flux<Playlist>> {

        private AllPlaylistsCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("Playlists"));
        }

        @Override
        protected Flux<Playlist> run() throws Exception {
            return playlistRepository.findAll();
        }

        @Override
        protected Flux<Playlist> getFallback() {
            return Flux.just(new Playlist());
        }

    }

    private class PlaylistByIdCommand extends HystrixCommand<Mono<Playlist>> {

        private UUID playlistId;

        private PlaylistByIdCommand(UUID id) {
            super(HystrixCommandGroupKey.Factory.asKey("Playlists"));
            this.playlistId = id;
        }

        @Override
        protected Mono<Playlist> run() throws Exception {
            return playlistRepository.findById(playlistId);
        }

        @Override
        protected Mono<Playlist> getFallback() {
            return Mono.just(new Playlist());
        }
    }
}
