package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.routers;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.handlers.SongHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class SongRouter {

    @Bean
    public RouterFunction<ServerResponse> route(SongHandler handler) {
        return RouterFunctions.route(GET("/getAllSongs"), handler::getAllSongs)
                .andRoute(GET("/hystrix.metrics"), handler::getHystrixMetrics);
    }
}
