package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice;

import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities.Playlist;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.hystrix.PlaylistHystrixCommands;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.repositories.PlaylistRepository;
import com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.services.PlaylistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@WebFluxTest
@ContextConfiguration(classes = {PlaylistService.class, PlaylistHystrixCommands.class})
@ExtendWith(SpringExtension.class)
public class PlaylistServiceTest {

    @MockBean
    private PlaylistRepository repository;

    @Autowired
    private PlaylistService service;

    @Test
    public void shouldReturnAllPlaylist() {
        Flux<Playlist> playlistFlux = Flux.just(Mockito.mock(Playlist.class), Mockito.mock(Playlist.class));
        Mockito.when(repository.findAll()).thenReturn(playlistFlux);

        Assertions.assertEquals(playlistFlux, service.getAll());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnPlaylistById() {
        Mono<Playlist> playlistMono = Mono.just(Mockito.mock(Playlist.class));
        UUID uuid = UUID.randomUUID();
        Mockito.when(repository.findById(uuid)).thenReturn(playlistMono);

        Assertions.assertEquals(playlistMono, service.findById(uuid));
        Mockito.verify(repository, Mockito.times(1)).findById(uuid);
    }
}
