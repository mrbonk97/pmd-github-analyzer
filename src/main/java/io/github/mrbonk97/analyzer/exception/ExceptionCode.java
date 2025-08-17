package io.github.mrbonk97.analyzer.exception;

public enum ExceptionCode {
    NO_URL("GitHub repository URL is required."),
    INVALID_URL("The provided GitHub repository URL is invalid."),
    INVALID_OPTION("The specified option is not supported."),
    UNAUTHORIZED_REPOSITORY("Unable to access or clone the provided repository."),
    INVALID_MAX_THREAD_OPTION("The --max-thread option must be a valid integer."),
    GIT_UNAVAILABLE("Git is not available or not installed."),
    FAILED_GET_COMMIT_LIST("Failed to retrieve the list of commits."),
    FAILED_WORKTREE_ADD("Failed to create a new Git worktree."),
    FAILED_WORKTREE_REMOVE("Failed to remove the Git worktree."),
    FAILED_SUMMARY_SAVE("Failed to save the analysis summary file."),
    FAILED_TO_COUNT_JAVA_FILES("Failed to count Java files in the repository."),
    FAILED_TO_DELETE_FILE("Failed to delete the specified file.");


    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}