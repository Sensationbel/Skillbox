import java.util.ArrayList;
import java.util.List;

public class Line {
    private String nameLine;
    private String number;

    public Line(String number, String nameLine){
        this.nameLine = nameLine;
        this.number = number;
    }

    public String getNameLine() {
        return nameLine;
    }

    public String getLine() {
        return number;
    }
}
