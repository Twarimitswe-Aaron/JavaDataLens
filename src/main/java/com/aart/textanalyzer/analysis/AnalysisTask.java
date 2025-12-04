package com.aart.textanalyzer.analysis;

import com.aart.textanalyzer.exception.AnalysisFailedException;
import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.AnalysisType;


public abstract class AnalysisTask<T> {

    private final AnalysisType type;

    protected AnalysisTask(AnalysisType type) {
        this.type = type;
    }


    public abstract AnalysisResult<T> analyze(String text) throws AnalysisFailedException;

    public AnalysisType getType() {
        return type;
    }
}
