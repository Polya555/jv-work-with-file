package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFile {
    private List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName),
                StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + fileName, e);
        }
        return lines;
    }

    public void getStatistic(String fromFileName, String toFileName) {
        List<String> lines = readLines(fromFileName);
        int supply = 0;
        int buy = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length != 2) {
                throw new RuntimeException("Is not working");
            }
            String operation = parts[0].trim();
            try {
                int n = Integer.parseInt(parts[1].trim());
                if ("supply".equals(operation)) {
                    supply += n;
                } else if ("buy".equals(operation)) {
                    buy += n;
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("results are incorrect");
            }
        }
        String report = buildReport(supply, buy);
        writeReport(toFileName, report);
    }

    private String buildReport(int supply, int buy) {
        StringBuilder sb = new StringBuilder();
        sb.append("supply,").append(supply).append(System.lineSeparator());
        sb.append("buy,").append(buy).append(System.lineSeparator());
        sb.append("result,").append(supply - buy);
        return sb.toString();
    }

    private void writeReport(String fileName, String report) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName),
                StandardCharsets.UTF_8)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write file " + fileName, e);
        }
    }
}
