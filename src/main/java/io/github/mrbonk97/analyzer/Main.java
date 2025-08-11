package io.github.mrbonk97.analyzer;

import io.github.mrbonk97.analyzer.service.GithubService;
import io.github.mrbonk97.analyzer.service.PmdService;
import io.github.mrbonk97.analyzer.utils.FileUtils;
import io.github.mrbonk97.analyzer.utils.StringUtils;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] options = StringUtils.getOptions(args);
        String githubRepo = options[0];
        String rulesets = options[1];
        String reportDir = options[2];
        String repoName = StringUtils.getRepoNameFromUrl(options[0]);
        int maxThreads = Integer.parseInt(options[3]);


        GithubService githubService = new GithubService();
        PmdService pmdService = new PmdService();


        githubService.cloneRepository(githubRepo);
        List<String> commits = githubService.getCommits(repoName);


        // Analyze each commit
        for (int i = 0; i < commits.size(); i++) {
            System.out.printf("Analyzing commit %d/%d\n", i + 1, commits.size());
            githubService.addWorkingTree(commits.get(i), repoName);
            pmdService.analyze(rulesets, reportDir, commits.get(i), maxThreads);
            githubService.removeWorkingTree(commits.get(i), repoName);
        }


        FileUtils.deleteFile("", "cache_pmd");
        pmdService.summarize(commits, reportDir);
    }
}