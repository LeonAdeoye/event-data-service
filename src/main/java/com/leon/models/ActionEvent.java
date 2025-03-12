package com.leon.models;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

public record ActionEvent (
        UUID testRunId,
        String type,
        int index,
        JsonNode payload,
        LocalDateTime runTime
) {
    private static final Logger logger = Logger.getLogger(ActionEvent.class.getName());

    public ActionEvent()
    {
        this(UUID.randomUUID(), "default", 0, null, LocalDateTime.now());
    }

    public ActionEvent(UUID testRunUUID, int index, String aDefault, Object o, LocalDateTime now) {
        this(testRunUUID, aDefault, index, null, now);
    }

    public ActionEvent(SerializableActionEvent serializableActionEvent) {
        this(serializableActionEvent.testRunId(), serializableActionEvent.index(), serializableActionEvent.type(), serializableActionEvent.payload(), LocalDateTime.now());
    }

    public static boolean isValid(ActionEvent actionEvent)
    {
        if(actionEvent == null)
        {
            logger.warning("Action event is null");
            return false;
        }
        if(actionEvent.testRunId() == null)
        {
            logger.warning("Action event test run Id is null");
            return false;
        }
        if(actionEvent.type() == null || actionEvent.type().isEmpty())
        {
            logger.warning("Action event type is null or empty");
            return false;
        }
        if(actionEvent.payload() == null)
        {
            logger.warning("Action event payload is null");
            return false;
        }
        if(actionEvent.index() < 0)
        {
            logger.warning("Action event index is less than 0");
            return false;
        }
        return true;
    }
}
