package com.aart.textanalyzer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String sourceFileName;

    private final List<AnalysisResult<?>> results;

    public Report(String sourceFileName) {
        this.sourceFileName = sourceFileName;
        this.results = new ArrayList<>();
    }


    public void addResult(AnalysisResult<?> result) {
        this.results.add(result);
    }

 
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
