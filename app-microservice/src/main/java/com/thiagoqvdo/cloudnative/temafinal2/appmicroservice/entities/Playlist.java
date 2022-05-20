package com.thiagoqvdo.cloudnative.temafinal2.appmicroservice.entities;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

public class Playlist {
    private UUID id;
    private List<UUID> songsId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getSongsId() {
        return songsId;
    }

    public void setSongsId(List<UUID> songsId) {
        this.songsId = songsId;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", songsId=" + songsId +
                '}';
    }
}
