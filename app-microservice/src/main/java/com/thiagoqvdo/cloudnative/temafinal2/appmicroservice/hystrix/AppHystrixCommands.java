package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.ribbon.AppRibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component("hystrixCommands")
public class AppHystrixCommands {

    @Autowired
    private AppRibbonClient ribbonClient;

    public Mono<Playlist> getPlaylistById(UUID id) {
        return new PlaylistByIdCommand(id).execute();
    }

    public Flux<Song> getSongListById(Mono<List<UUID>> idList) {
        return new SongByIdListCommand(idList).execute();
    }

    private class SongByIdListCommand extends HystrixCommand<Flux<Song>> {

        private Mono<List<UUID>> idList;

        private SongByIdListCommand(Mono<List<UUID>> idList) {
            super(HystrixCommandGroupKey.Factory.asKey("App"));
            this.idList = idList;
        }

        @Override
        protected Flux<Song> run() {
            return ribbonClient.getSongByIdList(this.idList);
        }

        @Override
        protected Flux<Song> getFallback() {
            return Flux.just(new Song());
        }
    }

    private class PlaylistByIdCommand extends HystrixCommand<Mono<Playlist>> {

        private UUID id;

        private PlaylistByIdCommand(UUID id) {
            super(HystrixCommandGroupKey.Factory.asKey("App"));
            this.id = id;
        }

        @Override
        protected Mono<Playlist> run() {
            return ribbonClient.getPlaylistById(id);
        }

        @Override
        protected Mono<Playlist> getFallback() {
            return Mono.just(new Playlist());
        }
    }
}
