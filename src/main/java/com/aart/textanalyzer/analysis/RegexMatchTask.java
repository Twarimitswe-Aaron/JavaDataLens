package com.aart.textanalyzer.analysis;

import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.AnalysisType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatchTask extends AnalysisTask<List<String>> {

    private final String regexPattern;

    public RegexMatchTask(String regexPattern) {
        super(AnalysisType.REGEX_SEARCH);
        this.regexPattern = regexPattern;
    }

    @Override
    public AnalysisResult<List<String>> analyze(String text) {
        long startTime = System.currentTimeMillis();
        List<String> matches = new ArrayList<>();

        if (text != null && !text.isEmpty()) {
       
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                matches.add(matcher.group());
            }
        }

        long endTime = System.currentTimeMillis();
        return new AnalysisResult<>(getType(), matches, endTime - startTime);
    }
}
