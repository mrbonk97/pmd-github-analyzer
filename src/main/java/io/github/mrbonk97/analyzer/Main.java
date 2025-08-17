package io.github.mrbonk97.analyzer;

import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.service.GithubService;
import io.github.mrbonk97.analyzer.service.PmdService;
import io.github.mrbonk97.analyzer.utils.StringUtils;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            AnalyzerOptions analyzerOptions = new AnalyzerOptions(args);
            GithubService githubService = new GithubService();
            PmdService pmdService = new PmdService(analyzerOptions.rulesets, analyzerOptions.maxThread);

            String repoName = StringUtils.getRepoNameFromUrl(analyzerOptions.url);

            System.out.println("Cloning Repository: " + repoName);
            githubService.cloneRepository(analyzerOptions.url);

            System.out.println("Fetching all commits");
            List<String> commits = githubService.getCommits(repoName);


            // Analyze each commit
            long start = System.nanoTime();

            for (int i = 0; i < commits.size(); i++) {
                long commitStart = System.nanoTime();
                System.out.printf("Analyzing commit %d/%d\n", i + 1, commits.size());
                githubService.addWorkingTree(commits.get(i), repoName);
                pmdService.analyze(analyzerOptions.reportFile, commits.get(i));
                githubService.removeWorkingTree(commits.get(i), repoName);
                long commitEnd = System.nanoTime();
                double commitTimeSec = (commitEnd - commitStart) / 1_000_000_000.0;
                System.out.printf("Commit %s analyzed in %.3f seconds\n", commits.get(i), commitTimeSec);
            }

            long end = System.nanoTime();
            double totalTimeSec = (end - start) / 1_000_000_000.0;
            double avgTimeSec = totalTimeSec / commits.size();


            System.out.println("Deleting temporary files");
            pmdService.deleteCacheFile();
            System.out.println("Summarizing Analysis");
            pmdService.summarize(analyzerOptions.url, analyzerOptions.reportFile);


            System.out.println("======================================");
            System.out.printf("Total analysis time: %.3f seconds\n", totalTimeSec);
            System.out.printf("Average per commit: %.3f seconds\n", avgTimeSec);
            System.out.println("======================================");
        } catch (CustomException e) {
            System.out.println("ERROR: " + e.getMessage());
        }


    }
}