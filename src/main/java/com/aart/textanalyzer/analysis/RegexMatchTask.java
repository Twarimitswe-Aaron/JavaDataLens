package com.aart.textanalyzer.analysis;

import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.AnalysisType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A concrete implementation of {@link AnalysisTask} that searches for regex
 * matches.
 */
public class RegexMatchTask extends AnalysisTask<List<String>> {

    private final String regexPattern;

    /**
     * Constructor.
     * 
     * @param regexPattern The regular expression pattern to search for.
     */
    public RegexMatchTask(String regexPattern) {
        super(AnalysisType.REGEX_SEARCH);
        this.regexPattern = regexPattern;
    }

    /**
     * Analyzes the text to find matches for the regex pattern.
     * <p>
     * <strong>Concept: Regular Expressions (Regex)</strong>
     * Uses {@link Pattern} and {@link Matcher} classes.
     * {@code Pattern.compile()} compiles the regex into a robust pattern.
     * {@code matcher.find()} scans the input sequence looking for the next
     * subsequence that matches the pattern.
     * </p>
     */
    @Override
    public AnalysisResult<List<String>> analyze(String text) {
        long startTime = System.currentTimeMillis();
        List<String> matches = new ArrayList<>();

        if (text != null && !text.isEmpty()) {
            // Compile the pattern (best practice to do this once if reused,
            // but here we do it per task as the task is the unit of work)
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(text);

            // Loop through all matches
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        }

        long endTime = System.currentTimeMillis();
        return new AnalysisResult<>(getType(), matches, endTime - startTime);
    }
}
