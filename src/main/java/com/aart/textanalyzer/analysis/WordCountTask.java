package com.aart.textanalyzer.analysis;

import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.AnalysisType;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCountTask extends AnalysisTask<Map<String, Integer>> {

    public WordCountTask() {
        super(AnalysisType.WORD_COUNT);
    }


    @Override
    public AnalysisResult<Map<String, Integer>> analyze(String text) {
        long startTime = System.currentTimeMillis();

        if (text == null || text.isEmpty()) {
            return new AnalysisResult<>(getType(), Collections.emptyMap(), 0);
        }

       
        Map<String, Integer> wordCounts = Arrays.stream(text.split("\\W+"))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toMap(
                        word -> word,
                        word -> 1,
                        Integer::sum
                ));


        Map<String, Integer> sortedCounts = wordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(20)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        long endTime = System.currentTimeMillis();
        return new AnalysisResult<>(getType(), sortedCounts, endTime - startTime);
    }
}
