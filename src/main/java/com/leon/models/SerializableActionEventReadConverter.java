package com.leon.models;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@ReadingConverter
public class SerializableActionEventReadConverter implements Converter<Document, SerializableActionEvent> {

    @Override
    public SerializableActionEvent convert(Document source) {
        UUID testRunId = source.get("_id", UUID.class);
        int index = source.getInteger("index");
        String type = source.getString("type");
        String payload = source.getString("payload");
        return new SerializableActionEvent(testRunId, index, type, payload);
    }
}