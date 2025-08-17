package io.github.mrbonk97.analyzer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandUtils {
    public static void runCommand(String[] command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while (br.readLine() != null) {
            }
        }

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

    }


    public static List<String> runCommandAndGetOutput(String[] command) throws IOException {
        List<String> output = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line.trim());
            }
        }

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        return output;
    }
}