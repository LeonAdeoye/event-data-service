package com.leon.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TestRun (
        List<ActionEvent> actionEvents,
        String testRunDescription,
        UUID testRunId,
        LocalDateTime testRunTime,
        String testRunner
) {
}
