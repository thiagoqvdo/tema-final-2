package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("getAll")
public class AllSongsCommand extends HystrixCommand<Flux<Song>> {

    @Autowired
    private SongRepository songRepository;

    protected AllSongsCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("Songs"));
    }

    public AllSongsCommand(SongRepository songRepository) {
        super(HystrixCommandGroupKey.Factory.asKey("Songs"));
        this.songRepository = songRepository;
    }

    @Override
    protected Flux<Song> run() throws Exception {
        return songRepository.findAll();
    }

    @Override
    protected Flux<Song> getFallback() {
        System.err.println(super.getFallback());
        return super.getFallback();
    }

    public Flux<Song> executeCommand() {
        return new AllSongsCommand(this.songRepository).execute();
    }
}
