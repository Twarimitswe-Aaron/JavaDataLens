package com.aart.textanalyzer.analysis;

import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.AnalysisType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A concrete implementation of {@link AnalysisTask} that counts word
 * frequencies.
 * <p>
 * <strong>Concept: Inheritance & Polymorphism</strong>
 * This class extends {@code AnalysisTask<Map<String, Integer>>}. It inherits
 * the structure
 * but provides a specific implementation for {@code analyze()}.
 * </p>
 */
public class WordCountTask extends AnalysisTask<Map<String, Integer>> {

    public WordCountTask() {
        super(AnalysisType.WORD_COUNT);
    }

    /**
     * Analyzes the text to count word frequencies.
     * <p>
     * <strong>Concept: String Manipulation</strong>
     * Uses {@code split()} with regex to break text into words, removing
     * punctuation.
     * Uses {@code toLowerCase()} for case-insensitive counting.
     * </p>
     * <p>
     * <strong>Concept: Collections (Map)</strong>
     * Uses a {@link HashMap} to store the word counts (Key: Word, Value: Count).
     * </p>
     * <p>
     * <strong>Concept: Streams API</strong>
     * Uses Java Streams to process the array of words, filter empty strings, and
     * collect them into a Map.
     * </p>
     */
    @Override
    public AnalysisResult<Map<String, Integer>> analyze(String text) {
        long startTime = System.currentTimeMillis();

        if (text == null || text.isEmpty()) {
            return new AnalysisResult<>(getType(), Collections.emptyMap(), 0);
        }

        // Split by non-word characters (punctuation, whitespace, etc.)
        // String[] words = text.toLowerCase().split("\\W+");

        // Let's use a more robust Stream approach
        Map<String, Integer> wordCounts = Arrays.stream(text.split("\\W+"))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                // Collect into a Map
                .collect(Collectors.toMap(
                        word -> word, // Key mapper
                        word -> 1, // Value mapper (start with 1)
                        Integer::sum // Merge function (if key exists, sum values)
                ));

        // Optional: Sort by frequency (descending) for the report
        // This demonstrates advanced Stream usage with Comparators
        Map<String, Integer> sortedCounts = wordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(20) // Limit to top 20 for brevity in this example
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // Use LinkedHashMap to preserve sort order
                ));

        long endTime = System.currentTimeMillis();
        return new AnalysisResult<>(getType(), sortedCounts, endTime - startTime);
    }
}
