public class Line implements Comparable {
    private Integer number;
    private String word;

    public Line(String line) {
        int pos = line.indexOf(".");
        number = Integer.parseInt(line.substring(0, pos));
        word = line.substring(pos + 2);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
