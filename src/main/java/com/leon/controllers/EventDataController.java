package com.leon.controllers;

import com.leon.models.ActionEvent;
import com.leon.models.TestRun;
import com.leon.services.ActionEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.leon.models.ActionEvent.isValid;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping("/event-data")
public class EventDataController {
    private static final Logger logger = LoggerFactory.getLogger(EventDataController.class);
    @Autowired
    ActionEventService actionEventService;

    @CrossOrigin
    @RequestMapping("/get")
    public ResponseEntity<TestRun> get() {
        return new ResponseEntity<TestRun>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping("/save")
    public ResponseEntity<ActionEvent> saveActionEvent(@RequestBody ActionEvent actionEvent) {
        if(!isValid(actionEvent)) {
            logger.error("Cannot save because action event is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ActionEvent>(actionEventService.save(actionEvent), HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(method = DELETE)
    public ResponseEntity<List<ActionEvent>> deleteTestRun(String testRunId) {
        if(testRunId == null || testRunId.isEmpty()) {
            logger.error("Cannot delete because test run ID is null or empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Deleting test run with ID: " + testRunId);
        return new ResponseEntity<>(actionEventService.delete(testRunId), HttpStatus.OK);
    }
}
