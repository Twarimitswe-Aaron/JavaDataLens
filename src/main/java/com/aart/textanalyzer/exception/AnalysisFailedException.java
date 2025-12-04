package com.aart.textanalyzer.exception;

/**
 * A custom checked exception that indicates a failure during the analysis
 * process.
 * <p>
 * <strong>Concept: Checked vs Unchecked Exceptions</strong>
 * This class extends {@link Exception}, making it a "Checked Exception".
 * This forces the caller to handle it (using try-catch) or declare it in the
 * method signature.
 * We use this because analysis failure (e.g., I/O error) is a condition a
 * reasonable application
 * might want to catch and recover from (or at least report gracefully).
 * Unchecked exceptions (extending RuntimeException) are usually for programming
 * errors (null pointers, etc.).
 * </p>
 */
public class AnalysisFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message The detail message.
     */
    public AnalysisFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * <strong>Concept: Exception Chaining</strong>
     * This constructor allows us to wrap another exception (the "cause", e.g., an
     * IOException)
     * inside this custom exception. This preserves the original stack trace while
     * providing
     * a more specific error type to the upper layers.
     * </p>
     * 
     * @param message The detail message.
     * @param cause   The cause.
     */
    public AnalysisFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
