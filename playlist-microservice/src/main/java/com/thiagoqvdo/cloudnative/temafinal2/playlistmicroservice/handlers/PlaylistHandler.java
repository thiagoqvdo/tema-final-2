package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.handlers;

import com.netflix.hystrix.metric.consumer.HystrixDashboardStream;
import com.netflix.hystrix.serial.SerialHystrixDashboardData;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.services.PlaylistService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.RxReactiveStreams;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PlaylistHandler {

    @Autowired
    @Qualifier("service")
    private PlaylistService service;

    public Mono<ServerResponse> getAllSongs(ServerRequest request) {
        return ok().body(service.getAll(), Playlist.class);
    }

    public Mono<ServerResponse> getSongById(ServerRequest request) {
        return ok().body(service.findById(UUID.fromString(request.queryParam("id").get())), Playlist.class);
    }

    public Mono<ServerResponse> getHystrixMetrics(ServerRequest request) {
        Observable<String> serializedDashboardData = HystrixDashboardStream.getInstance().observe()
                .concatMap(dashboardData -> Observable.from(SerialHystrixDashboardData.toMultipleJsonStrings(dashboardData)));
        Publisher<String> publisher = RxReactiveStreams.toPublisher(serializedDashboardData);

        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(publisher, String.class);
    }
}
