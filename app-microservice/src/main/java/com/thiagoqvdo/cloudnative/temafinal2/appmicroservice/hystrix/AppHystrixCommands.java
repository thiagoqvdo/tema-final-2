package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.hystrix;

import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component("hystrixCommands")
public class AppHystrixCommands {

    @Autowired
    private PlaylistByIdCommand playlistByIdCommand;

    @Autowired
    private SongByIdListCommand songByIdListCommand;

    public Mono<Playlist> execute(UUID id) {
        return playlistByIdCommand.executeCommand(id);
    }

    public Flux<Song> execute(Mono<List<UUID>> idList) {
        return songByIdListCommand.executeCommand(idList);
    }
}
