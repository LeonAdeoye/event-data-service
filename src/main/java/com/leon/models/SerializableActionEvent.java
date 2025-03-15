package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("ActionEvent")
@CompoundIndex(name = "test_run_and_index", def = "{'ActionEventId.id' : 1, 'ActionEventId.index': 1}")
public record SerializableActionEvent(
    @Id ActionEventId actionEventId,
    String type,
    String payload
)
{
    public SerializableActionEvent(ActionEvent actionEvent) {
        this(new ActionEventId(actionEvent.id(), actionEvent.index()),
                actionEvent.type(),
                actionEvent.payload() != null ? actionEvent.payload().toString() : "{}");
    }

    public SerializableActionEvent() {
        this(new ActionEvent());
    }

    public SerializableActionEvent(ActionEventId actionEventId, String type, String payload) {
        this.actionEventId = new ActionEventId(actionEventId.id(), actionEventId.index());
        this.type = type;
        this.payload = payload;
    }
}
