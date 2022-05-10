package com.thiagoqvdo.cloudnative.temafinal2.playlistmicroservice.entities;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table(value = "playlist")
public class Playlist {

    @PrimaryKey(value = "id")
    private UUID id;
    @Column(value = "songs")
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
