package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.hystrix;

import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.exceptions.InvalidHystrixCommandException;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component("hystrixCommands")
public class PlaylistHystrixCommands {

    @Autowired
    @Qualifier("allPlaylists")
    private AllPlaylistsCommand allPlaylistsCommand;

    @Autowired
    @Qualifier("playlistById")
    private PlaylistByIdCommand playlistByIdCommand;

    public Flux<Playlist> execute(HystrixCommandType command) {
        switch (command) {
            case GET_ALL: return allPlaylistsCommand.executeCommand();
            default: throw new InvalidHystrixCommandException("Invalid command use " + command);
        }
    }

    public Publisher<Playlist> execute(HystrixCommandType command, Object parameter) {
        switch (command) {
            case FIND_BY_ID: return playlistByIdCommand.executeCommand((UUID) parameter);
            default: throw new InvalidHystrixCommandException("Invalid command use " + command);
        }
    }

}
