package com.leon.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.leon.models.ActionEvent;
import com.leon.services.ActionEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static com.leon.models.ActionEvent.isValid;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/action-event")
public class EventDataController {
    private static final Logger logger = LoggerFactory.getLogger(EventDataController.class);
    @Autowired
    ActionEventService actionEventService;

    @CrossOrigin
    @RequestMapping(method = GET, produces = "application/json")
    public ResponseEntity<List<ActionEvent>> getAllActionEvents() {
        return new ResponseEntity<>(actionEventService.getAllActionEvents(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/get/{testRunId}", produces = "application/json", method=GET)
    public ResponseEntity<List<ActionEvent>> getAllActionEventsForTestRun(@PathVariable String testRunId) {
        if(testRunId == null || testRunId.isEmpty()) {
            logger.error("Cannot get all action events for test run because test run ID is null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Received request to retrieve action events with test run Id: " + testRunId);
        return new ResponseEntity<>(actionEventService.getActionEventsForTestRun(testRunId), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/compare", produces = "application/json", method=GET)
    public ResponseEntity<JsonNode> compare(@RequestParam String firstTestRunId, @RequestParam String secondTestRunId) {
        if(firstTestRunId == null || firstTestRunId.isEmpty()) {
            logger.error("Cannot compare because first test run ID is null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(secondTestRunId == null || secondTestRunId.isEmpty()) {
            logger.error("Cannot compare because second test run ID is null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Received request to compare test run: " + firstTestRunId + " with test run: " + secondTestRunId);
        return new ResponseEntity<>(actionEventService.compare(firstTestRunId, secondTestRunId), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<ActionEvent> saveActionEvent(@RequestBody ActionEvent actionEvent) {
        if(!isValid(actionEvent)) {
            logger.error("Cannot save because action event is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Received request to save action event: " + actionEvent);
        return new ResponseEntity<>(actionEventService.save(actionEvent), HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(method = DELETE, produces = "application/json")
    public ResponseEntity<List<ActionEvent>> deleteTestRun(@RequestParam String testRunId) {
        if(testRunId == null || testRunId.isEmpty()) {
            logger.error("Cannot delete because test run ID is null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Received request to delete action events with test run Id: " + testRunId);
        return new ResponseEntity<>(actionEventService.delete(testRunId), HttpStatus.OK);
    }
}
