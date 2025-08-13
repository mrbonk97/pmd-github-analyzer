# PMD GitHub Analyzer

Perform **static code analysis** on any Java GitHub repository using **PMD**.

---

## Features

- Analyze any Java GitHub repository commit by commit
- Generate detailed JSON reports for each commit
- Configurable PMD rulesets and threading options
- Run via **Docker** or locally

---

## How to Use

### 1. Using Docker

**Build Docker Image**

```bash
docker build -t pmd-github-analyzer .
```

**Run Docker Container**

```bash
docker run --rm -v <host_path>:/app/report pmd-github-analyzer --url <REPO_URL>
```

**Example (Windows)**

```bash
docker run --rm -v C:\pmd-report:/app/report pmd-github-analyzer --url https://github.com/mrbonk97/pmd-github-analyzer.git
```

> `-v <host_path>:/app/report` maps the container's report directory to your host so you can access analysis results after execution.

> By default, the report files are created in the /app/report/ directory inside the container. If you change the --report-file option, make sure it matches the path you mount with docker run.

---

### 2. Using Locally

**Clone repository**

```bash
git clone https://github.com/mrbonk97/pmd-github-analyzer.git
cd pmd-github-analyzer
```

**Build project**

- **Linux/macOS:**

```bash
./gradlew shadowJar
```

- **Windows:**

```bash
gradlew.bat shadowJar
```

**Run library**

```bash
java -jar build/libs/analyzer-1.0-all.jar --url <REPO_URL>
```

---

## Analysis Report

- Static analysis results are saved in the directory specified by `--report-file`.
- Each commit generates a JSON file, and a **summary** file named `summarize.json` is created.

---

## Options

| Option          | Default                                      | Required | Description                                                     |
| --------------- | -------------------------------------------- | -------- | --------------------------------------------------------------- |
| `--url`         | None                                         | Yes      | GitHub repository URL                                           |
| `--rulesets`    | `rulesets/java/quickstart.xml`               | No       | PMD ruleset configuration file                                  |
| `--report-file` | `report`                                     | No       | Path to report JSON file or directory                           |
| `--max-thread`  | `Runtime.getRuntime().availableProcessors()` | No       | Maximum number of threads to use (default: number of CPU cores) |
