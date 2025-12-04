package com.aart.textanalyzer.concurrency;

import com.aart.textanalyzer.analysis.AnalysisTask;
import com.aart.textanalyzer.model.AnalysisResult;
import java.util.concurrent.Callable;

/**
 * A worker class that wraps an {@link AnalysisTask} and executes it.
 * <p>
 * <strong>Concept: Multithreading & Callable</strong>
 * Implements {@link Callable}, which is similar to {@link Runnable} but can
 * return a result
 * and throw a checked exception. This is perfect for tasks submitted to an
 * {@link java.util.concurrent.ExecutorService}.
 * </p>
 * <p>
 * <strong>Concept: Generics</strong>
 * The class is generic {@code <T>} so it can handle any type of analysis task
 * and return the correct result type.
 * </p>
 */
public class AnalysisWorker<T> implements Callable<AnalysisResult<T>> {

    private final AnalysisTask<T> task;
    private final String textToAnalyze;

    /**
     * Constructor.
     * 
     * @param task          The analysis task to execute.
     * @param textToAnalyze The text data to process.
     */
    public AnalysisWorker(AnalysisTask<T> task, String textToAnalyze) {
        this.task = task;
        this.textToAnalyze = textToAnalyze;
    }

    /**
     * Executes the task.
     * <p>
     * <strong>Concept: Exception Handling</strong>
     * The {@code call} method can throw Exception, allowing us to propagate errors
     * back to the main thread via the Future object.
     * </p>
     * 
     * @return The result of the analysis.
     * @throws Exception If the analysis fails.
     */
    @Override
    public AnalysisResult<T> call() throws Exception {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] starting task: " + task.getType());
        return task.analyze(textToAnalyze);
    }
}
