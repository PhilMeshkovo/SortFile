import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var fileName = new Generator().generate(10000);
        new Sorter().sort(fileName, 500);
    }
}
