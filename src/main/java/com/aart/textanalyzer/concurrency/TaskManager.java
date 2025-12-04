package com.aart.textanalyzer.concurrency;

import com.aart.textanalyzer.model.AnalysisResult;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Manages the execution of analysis tasks using a thread pool.
 * <p>
 * <strong>Concept: Executor Framework</strong>
 * Instead of manually creating {@code new Thread()}, we use an
 * {@link ExecutorService}.
 * This decouples task submission from task execution and manages a pool of
 * worker threads efficiently.
 * </p>
 */
public class TaskManager {

    private final ExecutorService executor;

    /**
     * Constructor.
     * Initializes the thread pool.
     * <p>
     * <strong>Concept: Fixed Thread Pool</strong>
     * We use {@code Executors.newFixedThreadPool} to limit the number of concurrent
     * threads.
     * Using {@code Runtime.getRuntime().availableProcessors()} ensures we don't
     * overload the CPU.
     * </p>
     */
    public TaskManager() {
        int processors = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(processors);
        System.out.println("TaskManager initialized with " + processors + " threads.");
    }

    /**
     * Submits a list of workers for execution and waits for their results.
     * <p>
     * <strong>Concept: Wildcards</strong>
     * Uses {@code List<? extends AnalysisWorker<?>>} (Upper Bounded Wildcard) to
     * accept a list
     * of any type of AnalysisWorker.
     * </p>
     * <p>
     * <strong>Concept: Future</strong>
     * {@link Future} represents the result of an asynchronous computation. We use
     * it to retrieve
     * the result once the task is complete.
     * </p>
     * 
     * @param workers The list of workers to execute.
     * @return A list of AnalysisResults.
     */
    public List<AnalysisResult<?>> executeAll(List<? extends AnalysisWorker<?>> workers) {
        List<Future<? extends AnalysisResult<?>>> futures = new ArrayList<>();
        List<AnalysisResult<?>> results = new ArrayList<>();

        // Submit all tasks
        for (AnalysisWorker<?> worker : workers) {
            // We need to cast to Callable because submit expects one.
            // Since AnalysisWorker implements Callable, this is safe but generic handling
            // is tricky.
            // A cleaner way is to use invokeAll, but let's show manual submission for
            // demonstration.
            futures.add(executor.submit(worker));
        }

        // Retrieve results
        for (Future<? extends AnalysisResult<?>> future : futures) {
            try {
                // future.get() blocks until the task is complete
                results.add(future.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                System.err.println("Task execution interrupted: " + e.getMessage());
            } catch (ExecutionException e) {
                // The exception thrown by the task is wrapped in ExecutionException
                System.err.println("Task failed: " + e.getCause().getMessage());
            }
        }

        return results;
    }

    /**
     * Shuts down the executor service.
     * <p>
     * <strong>Concept: Graceful Shutdown</strong>
     * Always shut down executors to allow the application to exit.
     * </p>
     */
    public void shutdown() {
        executor.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
