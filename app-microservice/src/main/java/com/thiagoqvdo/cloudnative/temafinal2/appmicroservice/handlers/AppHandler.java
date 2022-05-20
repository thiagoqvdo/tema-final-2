package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.handlers;

import com.netflix.hystrix.metric.consumer.HystrixDashboardStream;
import com.netflix.hystrix.serial.SerialHystrixDashboardData;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.services.AppService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.RxReactiveStreams;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AppHandler {

    @Autowired
    private AppService service;

    public Mono<ServerResponse> getSongsByPlaylistId(ServerRequest request) {
        return ok().body(service.getSongsByIdList(UUID.fromString(request.queryParam("id").get())), Song.class);
    }

    public Mono<ServerResponse> getHystrixMetrics(ServerRequest request) {
        Observable<String> serializedDashboardData = HystrixDashboardStream.getInstance().observe()
                .concatMap(dashboardData -> Observable.from(SerialHystrixDashboardData.toMultipleJsonStrings(dashboardData)));
        Publisher<String> publisher = RxReactiveStreams.toPublisher(serializedDashboardData);

        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(publisher, String.class);
    }
}
