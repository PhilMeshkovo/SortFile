import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Sorter {
    public void sort(String fileName, int resultLines) throws IOException {
        var files = splitFile(fileName, resultLines);
        sortPartFiles(files);
        mergeSortedFiles(files);
    }

    private void sortPartFiles(List<String> files) {
        for (String file : files) {
            try {
                final Path path = Paths.get(file);
                var sortedLines = Files.readAllLines(path)
                        .stream().map(Line::new).sorted().collect(Collectors.toList());
                Files.write(path, sortedLines.stream().map(Line::build).collect(Collectors.toList()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class LineState implements Comparable {
        public BufferedReader reader;
        public Line line;

        public LineState(BufferedReader reader, Line line) {
            this.reader = reader;
            this.line = line;
        }

        @Override
        public int compareTo(Object o) {
            int result = line.getWord().compareTo(((LineState) o).line.getWord());
            if (result != 0) {
                return result;
            }
            return line.getNumber().compareTo(((Sorter.LineState) o).line.getNumber());
        }
    }

    private void mergeSortedFiles(List<String> files) {
        var readers = files.stream().map(x -> {
            try {
                return new BufferedReader(new FileReader(x));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        List<Sorter.LineState> lineStates = readers.map(x -> {
                    try {
                        return new Sorter.LineState(x, new Line(x.readLine()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).collect(Collectors.toList());
        try (PrintWriter writer = new PrintWriter(Generator.FILE_PATH_PREFIX + "result.txt")) {
            while (lineStates.size() > 0) {
                Collections.sort(lineStates);
                var current = lineStates.get(0);
                writer.write(current.line.build() + "\n");

                String line = current.reader.readLine();
                if (line == null) {
                    lineStates.remove(current);
                } else {
                    current.line = new Line(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> splitFile(String fileName, int resultLines) throws IOException {
        List<String> stringList = new ArrayList<>();
        FileReader fr = new FileReader(fileName);
        try (BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            int partNumber = 0;
            while (line != null) {
                partNumber++;
                var partFileName = Generator.FILE_PATH_PREFIX + partNumber + ".txt";
                stringList.add(partFileName);
                try (PrintWriter writer = new PrintWriter(partFileName)) {
                    for (int i = 0; i < resultLines; i++) {
                        if (line == null) {
                            break;
                        }
                        writer.write(line + "\n");
                        line = reader.readLine();
                    }
                }
            }
        }
        return stringList;
    }
}
