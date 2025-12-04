package com.aart.textanalyzer;

import com.aart.textanalyzer.analysis.AnalysisTask;
import com.aart.textanalyzer.analysis.RegexMatchTask;
import com.aart.textanalyzer.analysis.WordCountTask;
import com.aart.textanalyzer.concurrency.AnalysisWorker;
import com.aart.textanalyzer.concurrency.TaskManager;
import com.aart.textanalyzer.model.AnalysisResult;
import com.aart.textanalyzer.model.Report;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainApplication {

    private static final String REPORT_DIR = "reports";
    private static final String REPORT_FILE = "analysis_report.dat";

    public static void main(String[] args) {
      
        if (args.length < 1) {
            System.out.println("Usage: java com.aart.textanalyzer.MainApplication <file_path>");
           
            System.out.println("No file provided. Creating and using 'sample.txt' for demonstration.");
            createSampleFile("sample.txt");
            args = new String[] { "sample.txt" };
        }

        String filePath = args[0];
        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            System.err.println("Error: File not found: " + filePath);
            return;
        }

        System.out.println("Starting Analysis for: " + filePath);

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        String fullText = contentBuilder.toString();

        List<AnalysisTask<?>> tasks = new ArrayList<>();
        tasks.add(new WordCountTask());
        tasks.add(new RegexMatchTask("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b"));
        tasks.add(new RegexMatchTask(
                "https?://(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"));

     
        List<AnalysisWorker<?>> workers = new ArrayList<>();
        for (AnalysisTask<?> task : tasks) {
            workers.add(new AnalysisWorker<>(task, fullText));
        }

     
        TaskManager taskManager = new TaskManager();
        List<AnalysisResult<?>> results = taskManager.executeAll(workers);
        taskManager.shutdown();

   
        Report report = new Report(inputFile.getName());
        for (AnalysisResult<?> result : results) {
            report.addResult(result);
        }

        System.out.println("\n" + report);

        
        saveReport(report);

        
        Report loadedReport = loadReport();
        if (loadedReport != null) {
            System.out.println("\n[Verification] Successfully loaded report from disk.");
            System.out.println("Loaded Report Source: " + loadedReport.getSourceFileName());
        }
    }

    private static void saveReport(Report report) {
        File dir = new File(REPORT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(REPORT_DIR + File.separator + REPORT_FILE))) {
            oos.writeObject(report);
            System.out.println("Report saved to: " + REPORT_DIR + File.separator + REPORT_FILE);
        } catch (IOException e) {
            System.err.println("Error saving report: " + e.getMessage());
        }
    }

    private static Report loadReport() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(REPORT_DIR + File.separator + REPORT_FILE))) {
            return (Report) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading report: " + e.getMessage());
            return null;
        }
    }

    private static void createSampleFile(String filename) {
        String content = "Hello World! This is a sample text file for the Java Text Analysis Tool.\n" +
                "It contains some emails like test@example.com and admin@site.org.\n" +
                "It also has some URLs like https://www.google.com and http://java.com.\n" +
                "Java is great. Java is powerful. Multithreading in Java is fun.";
        try {
            Files.write(Paths.get(filename), content.getBytes());
            System.out.println("Sample file created: " + filename);
        } catch (IOException e) {
            System.err.println("Could not create sample file: " + e.getMessage());
        }
    }
}
