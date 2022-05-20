package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.ribbon.AppRibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component("songByIdListCommand")
public class SongByIdListCommand extends HystrixCommand<Flux<Song>> {

    @Autowired
    private AppRibbonClient ribbonClient;
    private Mono<List<UUID>> idList;

    protected SongByIdListCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("App"));
    }

    private SongByIdListCommand(SongByIdListCommand command, Mono<List<UUID>> idList) {
        super(command.getCommandGroup());
        this.ribbonClient  = command.ribbonClient;
        this.idList = idList;
    }

    @Override
    protected Flux<Song> run() throws Exception {
        return ribbonClient.getSongByIdList(this.idList);
    }

    public Flux<Song> executeCommand(Mono<List<UUID>> idList) {
        return new SongByIdListCommand(this, idList).execute();
    }
}
