package com.leon.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/event-data")
public class EventDataController {
    private static final Logger logger = LoggerFactory.getLogger(EventDataController.class);
    @CrossOrigin
    @RequestMapping("/get")
    public ResponseEntity<String> getEventData() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping("/add")
    public ResponseEntity<String>  addEventData() {
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping("/delete")
    public ResponseEntity<String> deleteTestRun() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
