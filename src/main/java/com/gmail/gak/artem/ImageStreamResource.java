package com.gmail.gak.artem;

import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageStreamResource implements StreamResource.StreamSource {
    private ByteArrayOutputStream outputStream = null;

    public ImageStreamResource(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
