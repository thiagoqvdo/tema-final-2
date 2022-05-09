package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component("songByID")
public class SongByIdCommand extends HystrixCommand<Mono<Song>> {
    @Autowired
    private SongRepository songRepository;
    private UUID songID;

    protected SongByIdCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("Songs"));
    }

    public SongByIdCommand(SongRepository songRepository, UUID id) {
        super(HystrixCommandGroupKey.Factory.asKey("Songs"));
        this.songRepository = songRepository;
        this.songID = id;
    }

    @Override
    protected Mono<Song> run() throws Exception {
        return songRepository.findById(songID);
    }

    @Override
    protected Mono<Song> getFallback() {
        System.err.println(super.getFallback());
        return super.getFallback();
    }

    public Mono<Song> executeCommand(UUID id) {
        return new SongByIdCommand(this.songRepository, id).execute();
    }
}
