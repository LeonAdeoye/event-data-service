package com.leon.services;

import com.leon.models.ActionEvent;
import com.leon.models.SerializableActionEvent;
import com.leon.repositories.ActionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<ActionEvent> getAllActionEvents() {
        return actionEventRepository.findAll().stream().map(ActionEvent::new).toList();
    }

    public List<ActionEvent> getActionEventsForTestRun(String testRunId) {
        return actionEventRepository.findAllByActionEventId_Id(UUID.fromString(testRunId)).stream().map(ActionEvent::new).toList();
    }
}
