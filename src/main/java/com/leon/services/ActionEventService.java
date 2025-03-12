package com.leon.services;

import com.leon.models.ActionEvent;

import java.util.List;

public interface ActionEventService {
    List<ActionEvent> delete(String testRunId);

    ActionEvent save(ActionEvent actionEvent);

    List<ActionEvent> getAllActionEvents();
}
