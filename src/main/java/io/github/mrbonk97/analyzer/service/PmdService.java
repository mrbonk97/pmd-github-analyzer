package io.github.mrbonk97.analyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PmdService {
    public void analyze(String ruleset, String reportDir, String commit, int thread) {
        PMDConfiguration config = new PMDConfiguration();
        config.addInputPath(Path.of(commit));
        config.addRuleSet(ruleset);
        config.setReportFormat("json");
        config.setReportFile(Paths.get(reportDir, String.format("%s.json", commit)));
        config.setAnalysisCacheLocation("cache_pmd");
        config.setThreads(thread);

        try (PmdAnalysis pmd = PmdAnalysis.create(config)) {
            pmd.performAnalysis();
        }
    }

    public void summarize(List<String> commits, String reportDir) throws IOException {
        if (commits == null || commits.isEmpty()) {
            throw new RuntimeException("commit list is empty");
        }

        long commitCount = commits.size();
        long fileCount = 0;
        long violationCount = 0;
        Map<String, Long> ruleStats = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Summarizing commits...");
        // 1. <reportDir>/<commits>.json 을 순회하며 연산
        for (String oid : commits) {
            String fileName = oid + ".json";
            Path path = Paths.get(reportDir, fileName);

            if (!Files.exists(path)) {
                continue;
            }


            JsonNode root = mapper.readTree(path.toFile());
            JsonNode files = root.get("files");


            if (files == null || !files.isArray()) {
                continue;
            }

            fileCount += files.size();

            for (JsonNode fileNode : files) {
                JsonNode violations = fileNode.get("violations");
                if (violations != null && violations.isArray()) {
                    for (JsonNode violation : violations) {
                        violationCount++;
                        String rule = violation.get("rule").asText();
                        ruleStats.put(rule, ruleStats.getOrDefault(rule, 0L) + 1);
                    }
                }
            }


        }


        // calculate result
        double averageFileCount = (double) fileCount / commitCount;
        double averageViolationCount = (double) violationCount / commitCount;


        // save result to summarize.json
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("location", reportDir);


        ObjectNode repoStats = mapper.createObjectNode();
        repoStats.put("number_of_commits", commitCount);
        repoStats.put("avg_of_num_java_files", averageFileCount);
        repoStats.put("avg_of_num_warnings", averageViolationCount);
        rootNode.set("stat_of_repository", repoStats);


        ObjectNode warningsStats = mapper.createObjectNode();
        ruleStats.forEach(warningsStats::put);
        rootNode.set("stat_of_warnings", warningsStats);


        mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(reportDir, "summarize.json").toFile(), rootNode);
        System.out.printf("Summary saved to: %s/summarize.json\n", reportDir);

    }
}
