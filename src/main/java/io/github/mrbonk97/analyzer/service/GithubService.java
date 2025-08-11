package io.github.mrbonk97.analyzer.service;

import io.github.mrbonk97.analyzer.utils.CommandUtils;

import java.io.IOException;
import java.util.List;

public class GithubService {
    public GithubService() throws InterruptedException {
        isGitInstalled();
    }


    public void isGitInstalled() throws InterruptedException {
        String[] command = {"git", "--version"};
        try {
            CommandUtils.runCommand(command);
        } catch (IOException e) {
            throw new RuntimeException("Git is not installed");
        }
    }


    public void cloneRepository(String url) throws IOException, InterruptedException {
        try {
            String[] command = {"git", "ls-remote", url};
            CommandUtils.runCommand(command);
        } catch (IOException e) {
            throw new RuntimeException("Unable to access github repository");
        }


        System.out.println("Cloning repository: " + url);
        String[] command = {"git", "clone", "--bare", url};
        CommandUtils.runCommand(command);
    }


    public void addWorkingTree(String oid, String repo) throws IOException, InterruptedException {
        String[] command = {"git", String.format("--git-dir=%s", repo), "worktree", "add", "--detach", oid, oid};
        CommandUtils.runCommand(command);
    }


    public void removeWorkingTree(String oid, String repo) throws IOException, InterruptedException {
        String[] command = {"git", String.format("--git-dir=%s", repo), "worktree", "remove", oid, "--force"};
        CommandUtils.runCommand(command);
    }


    public List<String> getCommits(String repo) throws IOException, InterruptedException {
        System.out.println("Getting commits");
        String[] command = {"git", String.format("--git-dir=%s", repo), "rev-list", "--all"};
        return CommandUtils.runCommandAndGetOutput(command);
    }
}
