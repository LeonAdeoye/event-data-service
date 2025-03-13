package com.leon.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ActionEvent (
        UUID id,
        String type,
        int index,
        JsonNode payload,
        LocalDateTime runTime
) {
    private static final Logger logger = LoggerFactory.getLogger(ActionEvent.class.getName());

    public ActionEvent()
    {
        this(UUID.randomUUID(), "", 0, JsonNodeFactory.instance.objectNode(), LocalDateTime.now());
    }

    public ActionEvent(UUID id, String type, int index, JsonNode payload, LocalDateTime runTime) {
        this.id = id;
        this.type = type;
        this.index = index;
        this.payload = payload;
        this.runTime = runTime;
    }

    public ActionEvent(SerializableActionEvent serializableActionEvent) {
        this(serializableActionEvent.id(), serializableActionEvent.type(), serializableActionEvent.index(),
                (null != serializableActionEvent.payload() ? ActionEvent.parseNode(serializableActionEvent.payload()) : JsonNodeFactory.instance.objectNode()),
                LocalDateTime.now());
    }

    private static JsonNode parseNode(String payload) {
        try {
            return new ObjectMapper().readValue(payload, JsonNode.class);
        }
        catch (JsonProcessingException jpe) {
            logger.error("Failed to parse payload: {}", payload, jpe);
            return JsonNodeFactory.instance.objectNode();
        }
    }

    public static boolean isValid(ActionEvent actionEvent) {
        if(actionEvent == null) {
            logger.error("Action event is null");
            return false;
        }
        if(actionEvent.id() == null) {
            logger.error("Action event test run Id is null");
            return false;
        }
        if(actionEvent.type() == null || actionEvent.type().isEmpty()) {
            logger.error("Action event type is null or empty");
            return false;
        }
        if(actionEvent.payload() == null) {
            logger.error("Action event payload is null");
            return false;
        }
        if(actionEvent.index() < 0) {
            logger.error("Action event index is less than 0");
            return false;
        }
        return true;
    }
}
