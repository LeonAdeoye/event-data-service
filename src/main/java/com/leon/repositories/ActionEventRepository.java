package com.leon.repositories;

import com.leon.models.ActionEventId;
import com.leon.models.SerializableActionEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActionEventRepository extends MongoRepository<SerializableActionEvent, ActionEventId> {
    void deleteByActionEventId_Id(UUID id);
    List<SerializableActionEvent> findAllByActionEventId_Id(UUID id);
}
