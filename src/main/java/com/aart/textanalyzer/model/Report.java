package com.aart.textanalyzer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the final report containing results from all analysis tasks.
 * <p>
 * <strong>Concept: Composition</strong>
 * This class is composed of a list of {@link AnalysisResult} objects.
 * </p>
 * <p>
 * <strong>Concept: Collections (List/ArrayList)</strong>
 * We use an {@link ArrayList} to store a dynamic number of results.
 * </p>
 */
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String sourceFileName;
    // Using Wildcard (?) to allow AnalysisResult of any type
    private final List<AnalysisResult<?>> results;

    public Report(String sourceFileName) {
        this.sourceFileName = sourceFileName;
        this.results = new ArrayList<>();
    }

    /**
     * Adds a result to the report.
     * <p>
     * <strong>Concept: Synchronized Methods (Concurrency)</strong>
     * Although we will likely populate this after threads are done, if multiple
     * threads
     * were to add results simultaneously, we might need synchronization.
     * Here, we assume single-threaded aggregation or thread-safe usage.
     * </p>
     * 
     * @param result The analysis result to add.
     */
    public void addResult(AnalysisResult<?> result) {
        this.results.add(result);
    }

    /**
     * Returns an unmodifiable view of the results.
     * <p>
     * <strong>Concept: Encapsulation & Immutability</strong>
     * We return an unmodifiable list to prevent external code from modifying
     * the internal state of the Report directly.
     * </p>
     * 
     * @return List of analysis results.
     */
    public List<AnalysisResult<?>> getResults() {
        return Collections.unmodifiableList(results);
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Analysis Report for: %s ===%n", sourceFileName));
        for (AnalysisResult<?> result : results) {
            sb.append(result.toString()).append(System.lineSeparator());
        }
        sb.append("===================================");
        return sb.toString();
    }
}
