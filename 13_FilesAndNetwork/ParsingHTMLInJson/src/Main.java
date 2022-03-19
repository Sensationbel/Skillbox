import java.io.IOException;

public class Main {
    public static final String URL = "https://www.moscowmap.ru/metro.html#lines ";
    public static void main(String[] args) {

        try {
            ParsingHTML parsingHTML = new ParsingHTML(URL);
            System.out.println(parsingHTML.parsLine());
            System.out.println(parsingHTML.parsStation());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
