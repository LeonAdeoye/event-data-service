package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("ActionEvent")
public record SerializableActionEvent(
    @Id ActionEventId actionEventId,
    String type,
    String payload
)
{
    public SerializableActionEvent(ActionEvent actionEvent)
    {
        this(new ActionEventId(actionEvent.testRunId(), actionEvent.index()), actionEvent.type(), actionEvent.payload().toString());
    }
}
