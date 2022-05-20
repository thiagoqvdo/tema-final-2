package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice;

import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.entities.Song;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.hystrix.SongHystrixCommand;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.repositories.SongRepository;
import com.thiagoqvdo.cloudnative.temafinal2.songmicroservice.services.SongService;
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
import reactor.test.StepVerifier;

import java.util.UUID;

@WebFluxTest
@ContextConfiguration(classes = {SongService.class, SongHystrixCommand.class})
@ExtendWith(SpringExtension.class)
public class SongServiceTest {

    @MockBean
    private SongRepository repository;

    @Autowired
    private SongService service;

    @Test
    public void shouldReturnAllSongs() {
        Flux<Song> songFlux = Flux.just(Mockito.mock(Song.class), Mockito.mock(Song.class));
        Mockito.when(repository.findAll()).thenReturn(songFlux);

        Assertions.assertEquals(songFlux, service.getAllSongs());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnSongById() {
        UUID uuid = UUID.randomUUID();
        Mono<Song> expectedSong = Mono.just(Mockito.mock(Song.class));
        Mockito.when(repository.findById(uuid)).thenReturn(expectedSong);

        Assertions.assertEquals(expectedSong, service.getSongById(uuid));
        Mockito.verify(repository, Mockito.times(1)).findById(uuid);
    }

    @Test
    public void shouldReturnSongList() {
        UUID[] uuids = new UUID[]{UUID.randomUUID(), UUID.randomUUID()};
        Song[] songs = new Song[]{Mockito.mock(Song.class), Mockito.mock(Song.class)};

        for (int i = 0; i < uuids.length; i++) {
            Mono<Song> songMono = Mono.just(songs[i]);
            Mockito.when(repository.findById(uuids[i])).thenReturn(songMono);
        }
        StepVerifier.create(service.getSongList(Flux.fromArray(uuids)))
                .expectNext(songs[0])
                .expectNext(songs[1])
                .expectComplete()
                .verify();

        Mockito.verify(repository, Mockito.times(2)).findById(Mockito.any(UUID.class));
    }
}
