package com.aart.textanalyzer.concurrency;

import com.aart.textanalyzer.model.AnalysisResult;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TaskManager {

    private final ExecutorService executor;

  
    public TaskManager() {
        int processors = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(processors);
        System.out.println("TaskManager initialized with " + processors + " threads.");
    }

   
    public List<AnalysisResult<?>> executeAll(List<? extends AnalysisWorker<?>> workers) {
        List<Future<? extends AnalysisResult<?>>> futures = new ArrayList<>();
        List<AnalysisResult<?>> results = new ArrayList<>();

      
        for (AnalysisWorker<?> worker : workers) {

            futures.add(executor.submit(worker));
        }

   
        for (Future<? extends AnalysisResult<?>> future : futures) {
            try {

                results.add(future.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Task execution interrupted: " + e.getMessage());
            } catch (ExecutionException e) {

                System.err.println("Task failed: " + e.getCause().getMessage());
            }
        }

        return results;
    }


    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
