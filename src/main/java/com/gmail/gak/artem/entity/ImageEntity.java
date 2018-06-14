package com.gmail.gak.artem.entity;

import com.vaadin.server.StreamResource;

import java.util.Objects;

public class ImageEntity {
    private long id;
    private StreamResource resource;

    public ImageEntity(StreamResource resource) {
        this.id = generateId();
        this.resource = resource;
    }

    public ImageEntity() {
        this.id = generateId();
    }

    private long generateId() {
        return System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return id == that.id &&
                Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, resource);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StreamResource getResource() {
        return resource;
    }

    public void setResource(StreamResource resource) {
        this.resource = resource;
    }
}
