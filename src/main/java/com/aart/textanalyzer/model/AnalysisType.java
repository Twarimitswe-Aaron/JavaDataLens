package com.aart.textanalyzer.model;

/**
 * Represents the different types of analysis that can be performed on the text.
 * <p>
 * <strong>Concept: Enums</strong>
 * Enums (Enumerations) are a special Java type used to define collections of
 * constants.
 * They provide type safety, meaning you can't pass an invalid string or integer
 * where an
 * AnalysisType is expected. This prevents runtime errors caused by typos (e.g.,
 * "WORD_COUNT" vs "WORDCOUNT").
 * </p>
 */
public enum AnalysisType {
    /**
     * Counts the frequency of each word in the text.
     */
    WORD_COUNT("Word Frequency Analysis"),

    /**
     * Searches for specific patterns (like emails or URLs) using Regular
     * Expressions.
     */
    REGEX_SEARCH("Pattern Matching Analysis"),

    /**
     * Counts the number of sentences in the text.
     */
    SENTENCE_COUNT("Sentence Count Analysis");

    private final String description;

    /**
     * Constructor for the Enum.
     * Enums can have fields, constructors, and methods, just like regular classes.
     * 
     * @param description A human-readable description of the analysis type.
     */
    AnalysisType(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the analysis type.
     * 
     * @return The description string.
     */
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
