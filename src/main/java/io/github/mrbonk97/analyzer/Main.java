package io.github.mrbonk97.analyzer;

import io.github.mrbonk97.analyzer.service.GithubService;
import io.github.mrbonk97.analyzer.service.PmdService;
import io.github.mrbonk97.analyzer.utils.StringUtils;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] _args = StringUtils.getArguments(args);
        String githubRepo = _args[0];
        String rulesets = _args[1];
        String reportDir = _args[2];
        String repoName = StringUtils.getRepoNameFromUrl(_args[0]);
        int maxThreads = Integer.parseInt(_args[3]);


        GithubService githubService = new GithubService();
        PmdService pmdService = new PmdService();


        githubService.cloneRepository(githubRepo);
        List<String> commits = githubService.getCommits(repoName);


        for (int i = 0; i < commits.size(); i++) {
            System.out.printf("Analyzing commit %d/%d\n", i + 1, commits.size());
            githubService.addWorkingTree(commits.get(i), repoName);
            pmdService.analyze(rulesets, reportDir, commits.get(i), maxThreads);
            githubService.removeWorkingTree(commits.get(i), repoName);

        }

        pmdService.summarize(commits, reportDir);
    }
}