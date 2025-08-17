package io.github.mrbonk97.analyzer.service;

import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.exception.ExceptionCode;
import io.github.mrbonk97.analyzer.utils.CommandUtils;

import java.io.IOException;
import java.util.List;

public class GithubService {
    public GithubService() {
        isGitInstalled();
    }


    public void isGitInstalled() {
        String[] command = {"git", "--version"};

        try {
            CommandUtils.runCommand(command);
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.GIT_UNAVAILABLE);
        }
    }


    public void cloneRepository(String url) {
        String[] command = {"git", "clone", "--bare", url};

        try {
            CommandUtils.runCommand(command);
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.UNAUTHORIZED_REPOSITORY);
        }
    }


    public void addWorkingTree(String oid, String repo) {
        String[] command = {"git", String.format("--git-dir=%s", repo), "worktree", "add", "--detach", oid, oid};

        try {
            CommandUtils.runCommand(command);
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.FAILED_WORKTREE_ADD);
        }

    }


    public void removeWorkingTree(String oid, String repo) {
        String[] command = {"git", String.format("--git-dir=%s", repo), "worktree", "remove", oid, "--force"};

        try {
            CommandUtils.runCommand(command);
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.FAILED_WORKTREE_REMOVE);
        }
    }

    public List<String> getCommits(String repo) {
        String[] command = {"git", String.format("--git-dir=%s", repo), "rev-list", "--all"};

        try {
            return CommandUtils.runCommandAndGetOutput(command);
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.FAILED_GET_COMMIT_LIST);
        }

    }
}
