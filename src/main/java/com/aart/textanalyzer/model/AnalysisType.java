package com.aart.textanalyzer.model;

public enum AnalysisType {
    WORD_COUNT("Word Frequency Analysis"),
    REGEX_SEARCH("Pattern Matching Analysis"),
    SENTENCE_COUNT("Sentence Count Analysis");

    private final String description;

    AnalysisType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "AnalysisType{" +
                "name='" + name() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
