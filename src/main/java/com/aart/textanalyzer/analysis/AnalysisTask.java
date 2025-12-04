package com.aart.textanalyzer.analysis;

import com.aart.textanalyzer.exception.AnalysisFailedException;
import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.AnalysisType;

/**
 * Abstract base class for all analysis tasks.
 * <p>
 * <strong>Concept: Abstraction</strong>
 * This class defines the common structure and contract for any analysis task.
 * It hides the complexity of how specific analyses are performed.
 * Users of this class only need to know about the {@code analyze} method.
 * </p>
 * <p>
 * <strong>Concept: Generics</strong>
 * The class is generic {@code <T>} to allow different tasks to return different
 * result types.
 * </p>
 * 
 * @param <T> The type of data returned by this analysis.
 */
public abstract class AnalysisTask<T> {

    private final AnalysisType type;

    /**
     * Constructor.
     * 
     * @param type The type of analysis this task performs.
     */
    protected AnalysisTask(AnalysisType type) {
        this.type = type;
    }

    /**
     * Performs the analysis on the given text.
     * <p>
     * <strong>Concept: Abstract Methods</strong>
     * This method is abstract, forcing all subclasses (concrete implementations) to
     * provide
     * their own logic for how to analyze the text.
     * </p>
     * 
     * @param text The text to analyze.
     * @return The result of the analysis wrapped in an {@link AnalysisResult}
     *         object.
     * @throws AnalysisFailedException If the analysis fails.
     */
    public abstract AnalysisResult<T> analyze(String text) throws AnalysisFailedException;

    /**
     * Gets the analysis type.
     * 
     * @return The AnalysisType.
     */
    public AnalysisType getType() {
        return type;
    }
}
