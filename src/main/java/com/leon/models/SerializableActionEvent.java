package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("ActionEvent")
public record SerializableActionEvent(
    @Id UUID testRunId,
    int index,
    String type,
    String payload
)
{
    public SerializableActionEvent(ActionEvent actionEvent)
    {
        this(actionEvent.testRunId(), actionEvent.index(), actionEvent.type(), actionEvent.payload().toString());
    }
}
