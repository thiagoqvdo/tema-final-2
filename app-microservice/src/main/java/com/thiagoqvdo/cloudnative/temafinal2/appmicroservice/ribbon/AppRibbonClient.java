package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.ribbon;

import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.feign.AppFeignClient;
import org.springframework.stereotype.Component;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.RxReactiveStreams;

import java.util.*;
import java.util.stream.Collectors;

@Component("ribbonClient")
public class AppRibbonClient {

    private EurekaClient eurekaClient;

    private ILoadBalancer getServerInstances(String serverName) {
        return LoadBalancerBuilder.newBuilder()
                .buildFixedServerListLoadBalancer(getEurekaClient().getApplication(serverName.toUpperCase())
                        .getInstances()
                        .stream()
                        .map(instanceInfo -> new Server(instanceInfo.getHostName(), instanceInfo.getPort()))
                        .collect(Collectors.toList()));
    }

    private EurekaClient getEurekaClient() {
        if (eurekaClient == null) {
            eurekaClient = DiscoveryManager.getInstance().getEurekaClient();
        }
        return eurekaClient;
    }

    public Mono<Playlist> getPlaylistById(UUID playlistId) {
        return Mono.fromDirect(RxReactiveStreams.toPublisher(LoadBalancerCommand.<Playlist>builder()
                .withLoadBalancer(getServerInstances("playlist-microservice"))
                .build()
                .submit(new ServerOperation<Playlist>() {
                    @Override
                    public Observable<Playlist> call(Server server) {
                        return RxReactiveStreams.toObservable(WebReactiveFeign.<AppFeignClient>builder()
                                .target(AppFeignClient.class, server.getHost() + ":" + server.getPort())
                                .playlistById(playlistId));
                    }
                })));
    }

    public Flux<Song> getSongByIdList(Mono<List<UUID>> songIdList) {
        return Flux.from(RxReactiveStreams.toPublisher(LoadBalancerCommand.<Song>builder()
                .withLoadBalancer(getServerInstances("song-microservice"))
                .build()
                .submit(new ServerOperation<Song>() {
                    @Override
                    public Observable<Song> call(Server server) {
                        return RxReactiveStreams.toObservable(WebReactiveFeign.<AppFeignClient>builder()
                                .target(AppFeignClient.class, server.getHost() + ":" + server.getPort())
                                .songByIdList(songIdList)
                        );
                    }
                })));
    }
}
