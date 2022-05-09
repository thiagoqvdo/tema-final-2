package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.handlers;

import com.netflix.hystrix.metric.consumer.HystrixDashboardStream;
import com.netflix.hystrix.serial.SerialHystrixDashboardData;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.services.SongService;
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

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class SongHandler {

    @Autowired
    @Qualifier("service")
    private SongService service;

    public Mono<ServerResponse> getAllSongs(ServerRequest request) {
        return ok().body(service.getAllSongs(), Song.class);
    }

    public Mono<ServerResponse> getHystrixMetrics(ServerRequest request) {
        Observable<String> serializedDashboardData = HystrixDashboardStream.getInstance().observe()
                .concatMap(dashboardData -> Observable.from(SerialHystrixDashboardData.toMultipleJsonStrings(dashboardData)));
        Publisher<String> publisher = RxReactiveStreams.toPublisher(serializedDashboardData);

        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(publisher, String.class);
    }

//    public Mono<ServerResponse> getSong(ServerRequest request) {
//        if (request.queryParam("id").isPresent()) {
//            return ok().body(service.getSongById(UUID.fromString(request.queryParam("id").get())), Song.class);
//        } else return badRequest().build();
//    }
}
