package com.leon.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.leon.models.ActionEvent;

import java.util.List;

public interface ActionEventService {
    List<ActionEvent> delete(String testRunId);
    ActionEvent save(ActionEvent actionEvent);
    List<ActionEvent> getAllActionEvents();
    List<ActionEvent> getActionEventsForTestRun(String testRunId);
    JsonNode compare(String firstTestRunId, String secondTestRunId);
}
