package com.leon.models;

import java.io.Serializable;
import java.util.UUID;

public record ActionEventId(UUID testRunId, int index) implements Serializable {
}
