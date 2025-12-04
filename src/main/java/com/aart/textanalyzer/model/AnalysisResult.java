package com.aart.textanalyzer.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A generic container for the result of an analysis task.
 * <p>
 * <strong>Concept: Generics</strong>
 * This class uses a generic type parameter {@code <T>} to allow it to store any
 * type of result data
 * (e.g., an Integer for a count, a Map for word frequencies, or a List of
 * strings).
 * This provides flexibility and type safety without casting.
 * </p>
 * <p>
 * <strong>Concept: Serialization (Data/Object Streams)</strong>
 * By implementing the {@link Serializable} interface, instances of this class
 * can be converted
 * into a byte stream and saved to a file (using
 * {@link java.io.ObjectOutputStream}).
 * This allows us to persist the state of our objects and reload them later.
 * </p>
 * 
 * @param <T> The type of the data held by this result.
 */
public class AnalysisResult<T> implements Serializable {

    // serialVersionUID is recommended for Serializable classes to verify that the
    // sender and receiver
    // of a serialized object have loaded classes for that object that are
    // compatible.
    private static final long serialVersionUID = 1L;

    private final AnalysisType type;
    private final T data;
    private final LocalDateTime timestamp;
    private final long executionTimeMs;

    /**
     * Constructs a new AnalysisResult.
     * 
     * @param type            The type of analysis performed.
     * @param data            The result data.
     * @param executionTimeMs The time taken to execute the analysis in
     *                        milliseconds.
     */
    public AnalysisResult(AnalysisType type, T data, long executionTimeMs) {
        this.type = type;
        this.data = data;
        this.executionTimeMs = executionTimeMs;
        this.timestamp = LocalDateTime.now(); // Capture the time of creation
    }

    // Getters using Encapsulation (protecting internal state)

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

    /**
     * Returns a formatted string representation of the result.
     * <p>
     * <strong>Concept: String Formatting</strong>
     * Uses {@link String#format} to create a structured string.
     * </p>
     */
    @Override
    public String toString() {
        return String.format("Result[Type=%s, Time=%s, Duration=%dms, Data=%s]",
                type, timestamp, executionTimeMs, data);
    }
}
