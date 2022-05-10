package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.routers;

import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.handlers.PlaylistHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class PlaylistRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PlaylistHandler handler) {
        return RouterFunctions.route(GET( "/playlist/all"), handler::getAllSongs)
                .andRoute(GET("/playlist/by_id")
                        .and(request -> request.queryParam("id").isPresent()), handler::getSongById)
                .andRoute(GET("/hystrix.stream"), handler::getHystrixMetrics);
    }
}
