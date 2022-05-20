package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.ribbon.AppRibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component("playlistByIdCommand")
public class PlaylistByIdCommand extends HystrixCommand<Mono<Playlist>> {

    @Autowired
    private AppRibbonClient ribbonClient;
    private UUID id;

    protected PlaylistByIdCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("App"));
    }

    private PlaylistByIdCommand(PlaylistByIdCommand command, UUID id) {
        super(HystrixCommandGroupKey.Factory.asKey("App"));
        this.ribbonClient = command.ribbonClient;
        this.id = id;
    }

    @Override
    protected Mono<Playlist> run() throws Exception {
        return ribbonClient.getPlaylistById(id);
    }

    @Override
    protected Mono<Playlist> getFallback() {
        System.err.println(super.getFallback());
        return super.getFallback();
    }

    public Mono<Playlist> executeCommand(UUID id) {
        return new PlaylistByIdCommand(this, id).execute();
    }

}
