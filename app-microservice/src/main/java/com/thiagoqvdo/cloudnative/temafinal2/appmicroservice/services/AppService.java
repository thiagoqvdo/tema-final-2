package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.services;

import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.hystrix.AppHystrixCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service("service")
public class AppService {

    @Autowired
    private AppHystrixCommands hystrixCommands;

    public Flux<Song> getSongsByIdList(UUID playlistId) {
        return hystrixCommands.execute(hystrixCommands.execute(playlistId)
                .map(playlist -> playlist.getSongsId()));
    }
}
