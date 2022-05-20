package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.routers;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.handlers.SongHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import rx.RxReactiveStreams;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class SongRouter {

    @Bean
    public RouterFunction<ServerResponse> route(SongHandler handler) {
        return RouterFunctions
                .route(GET("/song/all"), handler::getAllSongs)
                .andRoute(GET("/hystrix.stream"), handler::getHystrixMetrics)
                .andRoute(GET("/song/id")
                        .and(request -> request.queryParam("id").isPresent()), handler::getSong)
                .andRoute(POST("/song/list")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::getSongsByListId);
    }
}
