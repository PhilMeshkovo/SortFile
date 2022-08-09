public class Line implements Comparable {
    private final Integer number;
    private final String word;

    public Line(String line) {
        int pos = line.indexOf(".");
        number = Integer.parseInt(line.substring(0, pos));
        word = line.substring(pos + 2);
    }

    public Integer getNumber() {
        return number;
    }

    public String getWord() {
        return word;
    }

    @Override
    public int compareTo(Object o) {
        int result = word.compareTo(((Line) o).word);
        if (result != 0) {
            return result;
        }
        return number.compareTo(((Line) o).number);
    }

    public String build() {
        return number + ". " + word;
    }
}
