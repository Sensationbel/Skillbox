public class Station {
    private Line line;
    private String Station;

    public Station(Line line, String nameStation){
        this.line = line;
        this.Station = nameStation;
    }

    public Line getLine() {
        return line;
    }

    public String getNameStation() {
        return Station;
    }
}
