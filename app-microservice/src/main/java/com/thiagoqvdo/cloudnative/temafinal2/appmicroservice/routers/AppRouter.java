package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.routers;

import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.handlers.AppHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class AppRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AppHandler handler) {
        return RouterFunctions
                .route(GET("/app/playlist")
                        .and(request -> request.queryParam("id").isPresent()), handler::getSongsByPlaylistId)
                .andRoute(GET("/hystrix.stream"), handler::getHystrixMetrics);
    }
}
