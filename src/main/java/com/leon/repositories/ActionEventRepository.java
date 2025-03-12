package com.leon.repositories;

import com.leon.models.SerializableActionEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ActionEventRepository extends MongoRepository<SerializableActionEvent, UUID> {
}
