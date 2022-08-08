import org.apache.maven.surefire.shade.org.apache.commons.lang.RandomStringUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {
    public static final String FILE_PATH_PREFIX = "src/main/resources/";

    private Random random = new Random();
    private String[] words;

    public Generator() {
        words = new String[10000];
        for (int i = 0; i < words.length; i++) {
            words[i] = RandomStringUtils.random((int) (Math.random() * 80) + 20, 'a', 'z', true, false);
        }
    }

    public String generate(int linesCount) {
        var fileName = FILE_PATH_PREFIX + "L" + linesCount + ".txt";
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int i = 0; i < linesCount; i++) {
                writer.write(generateNumber() + ". " + generateString() + "\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    private String generateString() {
        return words[random.nextInt(words.length)];
    }

    private String generateNumber() {
        return String.valueOf(random.nextInt(10000));
    }
}
