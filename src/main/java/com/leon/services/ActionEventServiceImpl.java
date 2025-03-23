package com.leon.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leon.models.ActionEvent;
import com.leon.models.SerializableActionEvent;
import com.leon.repositories.ActionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ActionEventServiceImpl implements ActionEventService {
    @Autowired
    private ActionEventRepository actionEventRepository;
    @Override
    public List<ActionEvent> delete(String testRunId) {
        actionEventRepository.deleteByActionEventId_Id(UUID.fromString(testRunId));
        return getAllActionEvents();
    }
    @Override
    public ActionEvent save(ActionEvent actionEvent) {
        actionEventRepository.save(new SerializableActionEvent(actionEvent));
        return actionEvent;
    }
    @Override
    public List<ActionEvent> getAllActionEvents() {
        return actionEventRepository.findAll().stream().map(ActionEvent::new).toList();
    }
    @Override
    public List<ActionEvent> getActionEventsForTestRun(String testRunId) {
        return actionEventRepository.findAllByActionEventId_Id(UUID.fromString(testRunId)).stream().map(ActionEvent::new).toList();
    }
    @Override
    public JsonNode compare(String firstTestRunId, String secondTestRunId) {
        List<ActionEvent> firstTestRunActionEvents = actionEventRepository.findAllByActionEventId_Id(UUID.fromString(firstTestRunId))
                .stream().map(ActionEvent::new).sorted(Comparator.comparing(ActionEvent::index)).toList();

        List<ActionEvent> secondTestRunActionEvents = actionEventRepository.findAllByActionEventId_Id(UUID.fromString(secondTestRunId))
                .stream().map(ActionEvent::new).sorted(Comparator.comparing(ActionEvent::index)).toList();

        if(firstTestRunActionEvents.size() != secondTestRunActionEvents.size())
            throw new IllegalArgumentException("Test runs have different number of action events.");

        return compare(firstTestRunActionEvents, secondTestRunActionEvents);
    }
    private static void addStateDifference(String stateType, JsonNode firstState, JsonNode secondState, ArrayNode differences, ObjectMapper objectMapper) {
        if (!firstState.equals(secondState)) {
            ObjectNode stateDifference = objectMapper.createObjectNode();
            stateDifference.set("first", firstState);
            stateDifference.set("second", secondState);
            stateDifference.put("state", stateType);
            differences.add(stateDifference);
        }
    }
    private static JsonNode compare(List<ActionEvent> firstTestRunActionEvents, List<ActionEvent> secondTestRunActionEvents) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode diffSummary = objectMapper.createArrayNode();
        ArrayNode differences = objectMapper.createArrayNode();

        for(int index = 0; index < firstTestRunActionEvents.size(); ++index) {

            ActionEvent firstTestRunActionEvent = firstTestRunActionEvents.get(index);
            ActionEvent secondTestRunActionEvent = secondTestRunActionEvents.get(index);

            if(!firstTestRunActionEvent.type().equals(secondTestRunActionEvent.type()))
                throw new IllegalArgumentException("The type of action event at index " + index + " is different between the two test runs.");
            if(!firstTestRunActionEvent.payload().equals(secondTestRunActionEvent.payload()))
                throw new IllegalArgumentException("The payload of action event at index " + index + " is different between the two test runs.");

            addStateDifference("next", firstTestRunActionEvent.nextState(), secondTestRunActionEvent.nextState(), differences, objectMapper);
            addStateDifference("previous", firstTestRunActionEvent.prevState(), secondTestRunActionEvent.prevState(), differences, objectMapper);

            if(differences.size() > 0) {
                ObjectNode parent = objectMapper.createObjectNode();
                parent.put("type", firstTestRunActionEvent.type());
                parent.set("payload", firstTestRunActionEvent.payload());
                parent.put("index", index);
                parent.set("differences", differences);
                diffSummary.add(parent);
            }
        }
        return diffSummary;
    }
}
