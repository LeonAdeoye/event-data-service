package com.leon.models;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@ReadingConverter
public class SerializableActionEventReadConverter implements Converter<Document, SerializableActionEvent> {
    @Override
    public SerializableActionEvent convert(Document source) {
        Document document = (Document) source.get("_id");
        UUID testRunId = document.get("_id", UUID.class);
        int index = document.getInteger("index");
        ActionEventId actionEventId = new ActionEventId(testRunId, index);
        String type = source.getString("type");
        String payload = source.getString("payload");
        Date date = source.getDate("runTime");
        LocalDateTime runTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String testRunDescription = source.getString("testRunDescription");
        String testRunnerName = source.getString("testRunnerName");
        String prevState = source.getString("prevState");
        String nextState = source.getString("nextState");
        return new SerializableActionEvent(actionEventId, type, payload, runTime, testRunDescription, testRunnerName, prevState, nextState);
    }
}