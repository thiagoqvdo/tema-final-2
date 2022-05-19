package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component("hystrixCommands")
public class SongHystrixCommand {
    @Autowired
    @Qualifier("getAll")
    private AllSongsCommand getAllSongsCommand;

    @Autowired
    @Qualifier("songById")
    private SongByIdCommand songByIdCommand;

    public Flux<Song> execute() {
        return getAllSongsCommand.executeCommand();
    }

    public Mono<Song> execute(UUID id) {
        return songByIdCommand.executeCommand(id);
    }
}
