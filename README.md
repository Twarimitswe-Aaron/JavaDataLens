# Text Analysis Tool

A multi-threaded Java text analysis application that demonstrates advanced Java concepts including concurrency, generics, I/O streams, and object serialization.

## Project Overview

This application analyzes text files and performs various analysis tasks concurrently using Java's executor framework. It showcases:

- **Multithreading & Concurrency**: Uses `ExecutorService` for parallel task execution
- **Generics**: Type-safe analysis tasks with wildcard usage
- **I/O Streams**: Efficient file reading and object serialization
- **Exception Handling**: Custom exceptions and proper error handling
- **Object-Oriented Design**: Abstract classes, polymorphism, and encapsulation

## Features

- **Word Count Analysis**: Counts total words, unique words, and identifies most frequent words
- **Regex Pattern Matching**: Finds emails, URLs, or custom patterns in text
- **Concurrent Execution**: Analyzes multiple aspects of text simultaneously using thread pools
- **Report Generation**: Creates serialized reports that can be saved and loaded
- **Sample Data**: Auto-generates sample files for demonstration

## Project Structure

```
TextAnalysisTool/
├── src/main/java/com/aart/textanalyzer/
│   ├── MainApplication.java           # Entry point
│   ├── analysis/
│   │   ├── AnalysisTask.java         # Abstract base for analysis tasks
│   │   ├── WordCountTask.java        # Word counting implementation
│   │   └── RegexMatchTask.java       # Pattern matching implementation
│   ├── concurrency/
│   │   ├── TaskManager.java          # Thread pool management
│   │   └── AnalysisWorker.java       # Callable wrapper for tasks
│   ├── model/
│   │   ├── AnalysisResult.java       # Result container
│   │   ├── AnalysisType.java         # Analysis type enum
│   │   └── Report.java               # Serializable report
│   └── exception/
│       └── AnalysisFailedException.java  # Custom exception
└── README.md
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt with `javac` and `java` in PATH

## How to Build

1. Navigate to the source directory:

   ```cmd
   cd "d:\Documents\Notes2\JAVA\programs\This Year\Project\TextAnalysisTool\src\main\java"
   ```

2. Create the output directory:

   ```cmd
   mkdir ..\..\..\..\bin
   ```

3. **IMPORTANT**: Before compiling, rename the directory structure from `com\yourname` to `com\aart`:

   ```cmd
   move com\yourname com\aart
   ```

4. Compile all Java files:
   ```cmd
   javac -d ..\..\..\..\bin com\aart\textanalyzer\MainApplication.java com\aart\textanalyzer\analysis\*.java com\aart\textanalyzer\concurrency\*.java com\aart\textanalyzer\model\*.java com\aart\textanalyzer\exception\*.java
   ```

## How to Run

1. Navigate to the bin directory:

   ```cmd
   cd ..\..\..\..\bin
   ```

2. Run the application:

   ```cmd
   java com.aart.textanalyzer.MainApplication
   ```

   Or, to analyze a specific file:

   ```cmd
   java com.aart.textanalyzer.MainApplication path\to\yourfile.txt
   ```

## Sample Output

```
Starting Analysis for: sample.txt
TaskManager initialized with 4 threads.
Thread [pool-1-thread-1] starting task: WORD_COUNT
Thread [pool-1-thread-2] starting task: REGEX_MATCH
Thread [pool-1-thread-3] starting task: REGEX_MATCH

========================================
Text Analysis Report
Source File: sample.txt
Generated: 2025-12-04T07:45:00
========================================

Analysis: WORD_COUNT
--------------------------------
Total Words: 45
Unique Words: 32
Most Frequent:
  - Java: 3
  - is: 2

Analysis: REGEX_MATCH
--------------------------------
Found 2 matches
Matches:
  - test@example.com
  - admin@site.org

Report saved to: reports\analysis_report.dat
[Verification] Successfully loaded report from disk.
Loaded Report Source: sample.txt
```

## Key Concepts Demonstrated

### 1. **Executor Framework**

Uses a fixed thread pool to execute tasks concurrently without manually managing threads.

### 2. **Generics with Wildcards**

The `AnalysisTask<T>` is generic, allowing different types of results. Upper bounded wildcards (`? extends`) handle heterogeneous collections safely.

### 3. **Callable vs Runnable**

`AnalysisWorker` implements `Callable<AnalysisResult<T>>` which can return results and throw checked exceptions, unlike `Runnable`.

### 4. **Future for Async Results**

`Future` objects track pending task results and allow blocking retrieval with `.get()`.

### 5. **Object Serialization**

Reports implement `Serializable` and can be saved/loaded using `ObjectOutputStream` and `ObjectInputStream`.

## Customization

### Adding New Analysis Tasks

1. Extend `AnalysisTask<T>` where `T` is your result type
2. Implement the `analyze(String text)` method
3. Add your task to the list in `MainApplication`

Example:

```java
public class LineCountTask extends AnalysisTask<Integer> {
    @Override
    public AnalysisType getType() {
        return AnalysisType.LINE_COUNT;
    }

    @Override
    public AnalysisResult<Integer> analyze(Stringtext) {
        int lines = text.split("\n").length;
        return new AnalysisResult<>(getType(), lines);
    }
}
```

### Changing Thread Pool Size

Modify `TaskManager` constructor:

```java
// Use custom thread count instead of available processors
this.executor = Executors.newFixedThreadPool(8);
```

## Troubleshooting

**Issue**: `package com.aart ... does not exist`

- **Solution**: Ensure you've renamed the directory from `com\yourname` to `com\aart` before compiling

**Issue**: Classes not found when running

- **Solution**: Make sure you're running from the `bin` directory where compiled `.class` files are located

**Issue**: File not found error

- **Solution**: Provide the full path to the text file or run from the directory containing the file

## License

This is an educational project demonstrating Java programming concepts.

## Author

Created by Aart
