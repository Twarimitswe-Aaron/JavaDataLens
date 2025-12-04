package com.aart.textanalyzer.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AnalysisResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final AnalysisType type;
    private final T data;
    private final LocalDateTime timestamp;
    private final long executionTimeMs;

    public AnalysisResult(AnalysisType type, T data, long executionTimeMs) {
        this.type = type;
        this.data = data;
        this.executionTimeMs = executionTimeMs;
        this.timestamp = LocalDateTime.now();
    }

    public AnalysisType getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    @Override
    public String toString() {
        return String.format("Result[Type=%s, Time=%s, Duration=%dms, Data=%s]",
                type, timestamp, executionTimeMs, data);
    }
}
