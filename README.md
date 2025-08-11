# PMD Github Analyzer

Perform static code analysis on any Java GitHub repository using PMD.

### How to use

1. Clone repository

```shell
git clone https://github.com/mrbonk97/pmd-github-analyzer.git
```

2. Build project

- On Linux/macOS:

```shell
./gradlew shadowJar
```

- On Windows:

```shell
gradlew.bat shadowJar
```

3. Run library

```shell
java -jar build/libs/analyzer-1.0-all.jar --url <REPO_URL>
```

### Analysis Report

Static analysis results for each commit are saved in the path specified by `--report-file`, including a summary file named `summarize.json`.

### Options

| option          | default                                      | required | description                                                     |
| :-------------- | :------------------------------------------- | :------- | :-------------------------------------------------------------- |
| `--url`         | null                                         | O        | GitHub repository URL                                           |
| `--rulesets`    | `rulesets/java/quickstart.xml`               | X        | PMD ruleset configuration file                                  |
| `--report-file` | `report`                                     | X        | Path to report JSON file or directory                           |
| `--max-thread`  | `Runtime.getRuntime().availableProcessors()` | X        | Maximum number of threads to use (default: number of CPU cores) |
