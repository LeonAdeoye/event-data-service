package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("ActionEvent")
public record SerializableActionEvent(
    @Id UUID id,
    int index,
    String type,
    String payload
)
{
    public SerializableActionEvent(ActionEvent actionEvent) {
        this(actionEvent.id(), actionEvent.index(), actionEvent.type(), actionEvent.payload() != null ? actionEvent.payload().toString() : "{}");
    }

    public SerializableActionEvent() {
        this(new ActionEvent());
    }

    public SerializableActionEvent(UUID id, int index, String type, String payload) {
        this.id = id;
        this.index = index;
        this.type = type;
        this.payload = payload;
    }
}
