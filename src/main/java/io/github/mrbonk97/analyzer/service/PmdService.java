package io.github.mrbonk97.analyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.exception.ExceptionCode;
import io.github.mrbonk97.analyzer.utils.FileUtils;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PmdService {
    PMDConfiguration config;
    Map<String, Long> violationStatus = new HashMap<>();
    long fileCount = 0;
    long commitCount = 0;
    long warningCount = 0;

    public PmdService(String ruleset, int thread) {
        config = new PMDConfiguration();
        config.addRuleSet(ruleset);
        config.setReportFormat("json");

        Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir"), "cache_pmd");
        config.setAnalysisCacheLocation(tmpDir.toString());
        config.setThreads(thread);
    }

    public void analyze(String reportDir, String commit) {
        Path path = Paths.get(commit, "src", "main", "java");
        long commitFileCount = FileUtils.countJavaFileInDirectory(path);

        if (commitFileCount == 0) {
            System.out.println("Skipping Analysis on commit " + commit + ": no source files found");
            return;
        }

        fileCount += commitFileCount;
        config.setInputPathList(List.of(path));
        config.setReportFile(Paths.get(reportDir, String.format("%s.json", commit)));

        try (PmdAnalysis pmd = PmdAnalysis.create(config)) {
            Report report = pmd.performAnalysisAndCollectReport();
            commitCount++;

            List<RuleViolation> violations = report.getViolations();
            violations.forEach(violation -> {
                String violationName = violation.getRule().getName();
                violationStatus.put(violationName, violationStatus.getOrDefault(violationName, 0L) + 1);
                warningCount++;
            });
        }
    }


    public void summarize(String location, String reportDir) {
        if (commitCount == 0) {
            System.out.println("No commits analyzed, skipping summary.");
            return;
        }

        // calculate result
        double averageFileCount = Math.round(((double) fileCount / commitCount) * 10.0) / 10.0;
        double averageViolationCount = Math.round(((double) warningCount / commitCount) * 10.0) / 10.0;


        // save result to summarize.json
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("location", location);


        ObjectNode repoStats = mapper.createObjectNode();
        repoStats.put("number_of_commits", commitCount);
        repoStats.put("avg_of_num_java_files", averageFileCount);
        repoStats.put("avg_of_num_warnings", averageViolationCount);
        rootNode.set("stat_of_repository", repoStats);


        ObjectNode warningsStats = mapper.createObjectNode();
        violationStatus.forEach(warningsStats::put);
        rootNode.set("stat_of_warnings", warningsStats);


        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(reportDir, "summary.json").toFile(), rootNode);
            System.out.printf("Summary saved to: %s/summary.json\n", reportDir);
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.FAILED_SUMMARY_SAVE);

        }
    }


    public void deleteCacheFile() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        FileUtils.deleteFile(tmpDir, "cache_pmd");
    }
}
