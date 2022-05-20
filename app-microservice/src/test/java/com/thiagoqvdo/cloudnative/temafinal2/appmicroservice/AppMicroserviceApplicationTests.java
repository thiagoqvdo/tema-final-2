package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice;

import com.google.inject.Injector;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.transport.EurekaClientFactoryBuilder;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.guice.LifecycleInjectorBuilder;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.feign.AppFeignClient;
import com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.ribbon.AppRibbonClient;
import feign.Feign;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.RxReactiveStreams;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppMicroserviceApplicationTests {

    private AppRibbonClient ribbonClient;

    @Test
    void contextLoads() throws InterruptedException {
         WebReactiveFeign.<AppFeignClient>builder()
                .target(AppFeignClient.class, "localhost"+":"+"8081")
                .songByIdList(Mono.just(List.of(UUID.fromString("d0ff1220-d1f6-11ec-b309-c11cfa88b880"),
                        UUID.fromString("d0f9e200-d1f6-11ec-b309-c11cfa88b880")))).log().doOnError(Throwable::printStackTrace).subscribe(System.err::println);
         Thread.sleep(2000);
    }

    @Test
    public void setUp() throws InterruptedException {

    }

}
