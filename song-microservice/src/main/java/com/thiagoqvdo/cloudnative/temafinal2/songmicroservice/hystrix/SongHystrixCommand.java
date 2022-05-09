package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.enums.HystrixCommandType;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.exceptions.InvalidHystrixCommandException;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component("hystrixCommands")
public class SongHystrixCommand {
    @Autowired
    @Qualifier("getAll")
    private AllSongsCommand getAllSongsCommand;

    @Autowired
    @Qualifier("songByID")
    private SongByIdCommand songByIdCommand;

    public Flux<Song> execute(HystrixCommandType command) {
        switch (command) {
            case GET_ALL: return getAllSongsCommand.executeCommand();
            default: throw new InvalidHystrixCommandException("Hystrix command is invalid.");
        }
    }

    public Publisher<Song> execute(HystrixCommandType command, Object parameter) {
        switch (command) {
            case GET_BY_ID: return songByIdCommand.executeCommand((UUID) parameter);
            default: throw new InvalidHystrixCommandException("Hystrix command is invalid.");
        }
    }
}
