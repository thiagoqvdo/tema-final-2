package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.feign;

import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import feign.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@ReactiveFeignClient
@Headers({ "Accept: application/json" })
public interface AppFeignClient {

    @RequestLine("GET /playlist/id")
    Mono<Playlist> playlistById(@Param("id")UUID id);

    @RequestLine("POST /song/list")
    @Headers("Content-Type: application/json")
    Flux<Song> songByIdList(Mono<List<UUID>> idList);
}
