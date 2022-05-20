package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component("hystrixCommands")
public class SongHystrixCommand {

    @Autowired
    private SongRepository songRepository;

    public Flux<Song> getAll() {
        return new AllSongsCommand().execute();
    }

    public Mono<Song> getSongById(UUID id) {
        return new SongByIdCommand(id).execute();
    }

    private class AllSongsCommand extends HystrixCommand<Flux<Song>> {

        private AllSongsCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("Songs"));
        }

        @Override
        protected Flux<Song> run() {
            return songRepository.findAll();
        }

        @Override
        protected Flux<Song> getFallback() {
            return Flux.just(new Song());
        }
    }

    private class SongByIdCommand extends HystrixCommand<Mono<Song>> {

        private UUID id;

        private SongByIdCommand(UUID id) {
            super(HystrixCommandGroupKey.Factory.asKey("Songs"));
            this.id = id;
        }

        @Override
        protected Mono<Song> run() {
            return songRepository.findById(id);
        }

        @Override
        protected Mono<Song> getFallback() {
            return Mono.just(new Song());
        }
    }
}
